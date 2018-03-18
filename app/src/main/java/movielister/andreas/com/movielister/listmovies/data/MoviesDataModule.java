package movielister.andreas.com.movielister.listmovies.data;

import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import movielister.andreas.com.movielister.R;
import movielister.andreas.com.movielister.core.Logger;
import movielister.andreas.com.movielister.core.SchedulersProvider;
import movielister.andreas.com.movielister.listmovies.data.cache.CacheValidator;
import movielister.andreas.com.movielister.listmovies.data.cache.CachedMoviesDao;
import movielister.andreas.com.movielister.listmovies.data.cache.CachedMoviesModule;

@Module(includes = CachedMoviesModule.class)
public abstract class MoviesDataModule {

    @Provides
    static Gson provideGson() {
        return new GsonBuilder().
                registerTypeAdapterFactory(AdapterFactory.create())
                .create();
    }

    @Provides
    static ApiGenerator provideRetrofitApiGenerator(Resources resources, Gson gson) {
        return new RetrofitApiGenerator(resources.getString(R.string.movies_api_base_url), gson);
    }

    @Provides
    static MoviesApi provideMoviesApi(ApiGenerator apiGenerator) {
        return apiGenerator.createService(MoviesApi.class);
    }

    @Provides
    static MovieItemsToCachedMoviesMapper provideMovieItemsToCachedMoviesMapper() {
        return new MovieItemsToCachedMoviesMapper();
    }

    @Provides
    static CachedMoviesToMovieItemsMapper provideCachedMoviesToMovieItemsMapper() {
        return new CachedMoviesToMovieItemsMapper();
    }

    @Provides
    static MoviesRepository provideMoviesRepository(MoviesApi moviesApi,
                                                    CachedMoviesDao cachedMoviesDao,
                                                    MovieItemsToCachedMoviesMapper movieItemsToCachedMoviesMapper,
                                                    CachedMoviesToMovieItemsMapper cachedMoviesToMovieItemsMapper,
                                                    SchedulersProvider schedulersProvider,
                                                    Logger logger,
                                                    CacheValidator cacheValidator) {
        return new MoviesRepositoryImpl(moviesApi,
                cacheValidator,
                cachedMoviesDao,
                movieItemsToCachedMoviesMapper,
                cachedMoviesToMovieItemsMapper,
                schedulersProvider,
                logger);
    }
}
