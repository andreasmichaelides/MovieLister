package movielister.andreas.com.movielister.listmovies.data;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import movielister.andreas.com.movielister.core.Logger;
import movielister.andreas.com.movielister.core.SchedulersProvider;
import movielister.andreas.com.movielister.listmovies.data.cache.CacheValidator;
import movielister.andreas.com.movielister.listmovies.data.cache.CachedMovie;
import movielister.andreas.com.movielister.listmovies.data.cache.CachedMoviesDao;

class MoviesRepositoryImpl implements MoviesRepository {

    private final MoviesApi moviesApi;
    private final CacheValidator cacheValidator;
    private final CachedMoviesDao cachedMoviesDao;
    private final MovieItemsToCachedMoviesMapper movieItemsToCachedMoviesMapper;
    private final CachedMoviesToMovieItemsMapper cachedMoviesToMovieItemsMapper;
    private final SchedulersProvider schedulersProvider;
    private final Logger logger;

    MoviesRepositoryImpl(MoviesApi moviesApi,
                         CacheValidator cacheValidator,
                         CachedMoviesDao cachedMoviesDao,
                         MovieItemsToCachedMoviesMapper movieItemsToCachedMoviesMapper,
                         CachedMoviesToMovieItemsMapper cachedMoviesToMovieItemsMapper,
                         SchedulersProvider schedulersProvider,
                         Logger logger) {
        this.moviesApi = moviesApi;
        this.cacheValidator = cacheValidator;
        this.cachedMoviesDao = cachedMoviesDao;
        this.movieItemsToCachedMoviesMapper = movieItemsToCachedMoviesMapper;
        this.cachedMoviesToMovieItemsMapper = cachedMoviesToMovieItemsMapper;
        this.schedulersProvider = schedulersProvider;
        this.logger = logger;
    }

    @Override
    public Observable<List<MovieItem>> getMovies() {
        return Observable.merge(loadFromApi(), loadFromCache())
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.mainThread());
    }

    private Single<List<MovieItem>> cacheMovies(List<MovieItem> movieItems) {
        return movieItemsToCachedMoviesMapper.mapToCachedMovies(movieItems)
                .flatMap(cachedMovies -> updateCacheAndExpiryTime(cachedMovies)
                        .andThen(Single.just(movieItems)));
    }

    private Completable updateCacheAndExpiryTime(List<CachedMovie> cachedMovies) {
        return Completable.fromAction(() -> cachedMoviesDao.updateCache(cachedMovies))
                .andThen(cacheValidator.setCacheUpToDate());
    }

    private Observable<List<MovieItem>> loadFromApi() {
        return cacheValidator.isCacheValid()
                .filter(isValid -> !isValid)
                .flatMapSingle(ignored -> moviesApi.getMovies()
                        .map(MoviesResponse::data)
                        .flatMap(this::cacheMovies))
                .doOnNext(ignored -> logger.d(this, "Loaded from Api"));
    }

    private Observable<List<MovieItem>> loadFromCache() {
        return cacheValidator.isCacheValid()
                .filter(isValid -> isValid)
                .flatMap(ignored -> cachedMoviesDao.getCachedMovies().toObservable())
                .flatMapSingle(cachedMoviesToMovieItemsMapper::mapToMovieItems)
                .doOnNext(ignored -> logger.d(this, "Loaded from cache, " + cacheValidator.toString()));
    }
}
