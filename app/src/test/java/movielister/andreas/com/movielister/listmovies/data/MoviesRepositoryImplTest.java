package movielister.andreas.com.movielister.listmovies.data;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import movielister.andreas.com.movielister.core.Logger;
import movielister.andreas.com.movielister.core.SchedulersProvider;
import movielister.andreas.com.movielister.listmovies.data.cache.CacheValidator;
import movielister.andreas.com.movielister.listmovies.data.cache.CachedMovie;
import movielister.andreas.com.movielister.listmovies.data.cache.CachedMoviesDao;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MoviesRepositoryImplTest {

    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();

    @Mock
    private MoviesApi moviesApiMock;
    @Mock
    private CacheValidator cacheValidatorMock;
    @Mock
    private CachedMoviesDao cachedMoviesDaoMock;
    @Mock
    private MovieItemsToCachedMoviesMapper movieItemsToCachedMoviesMapperMock;
    @Mock
    private CachedMoviesToMovieItemsMapper cachedMoviesToMovieItemsMapperMock;
    @Mock
    private SchedulersProvider schedulersProviderMock;
    @Mock
    private Logger loggerMock;

    @InjectMocks
    private MoviesRepositoryImpl moviesRepository;

    @Before
    public void setUp() throws Exception {
        when(schedulersProviderMock.io()).thenReturn(Schedulers.trampoline());
        when(schedulersProviderMock.mainThread()).thenReturn(Schedulers.trampoline());
    }

    @Test
    public void whenCacheIsValidGetsMoviesFromApi() throws Exception {
        MovieItem movieItem = createMovieItem();
        List<MovieItem> expectedMovieItems = singletonList(movieItem);
        when(cacheValidatorMock.isCacheValid()).thenReturn(Observable.just(false));
        when(moviesApiMock.getMovies()).thenReturn(Single.just(createMoviesResponse(expectedMovieItems)));
        when(movieItemsToCachedMoviesMapperMock.mapToCachedMovies(expectedMovieItems))
                .thenReturn(Single.just(singletonList(createCachedMovie(movieItem))));
        when(cacheValidatorMock.setCacheUpToDate()).thenReturn(Completable.complete());

        TestObserver<List<MovieItem>> test = moviesRepository.getMovies().test();

        test.assertValue(expectedMovieItems)
                .assertNoErrors();
    }

    @Test
    public void whenCacheIsNotValidGetsMoviesFromCache() throws Exception {
        MovieItem movieItem = createMovieItem();
        List<MovieItem> expectedMovieItems = singletonList(movieItem);
        List<CachedMovie> cachedMovies = singletonList(createCachedMovie(movieItem));
        when(cacheValidatorMock.isCacheValid()).thenReturn(Observable.just(true));
        when(cachedMoviesDaoMock.getCachedMovies()).thenReturn(Single.just(cachedMovies));
        when(cachedMoviesToMovieItemsMapperMock.mapToMovieItems(cachedMovies))
                .thenReturn(Single.just(expectedMovieItems));

        TestObserver<List<MovieItem>> test = moviesRepository.getMovies().test();

        test.assertValue(expectedMovieItems)
                .assertNoErrors();
    }

    @Test
    public void updatesCache() throws Exception {
        MovieItem movieItem = createMovieItem();
        List<MovieItem> movieItems = singletonList(movieItem);
        when(cacheValidatorMock.isCacheValid()).thenReturn(Observable.just(false));
        when(moviesApiMock.getMovies()).thenReturn(Single.just(createMoviesResponse(movieItems)));
        when(movieItemsToCachedMoviesMapperMock.mapToCachedMovies(movieItems))
                .thenReturn(Single.just(singletonList(createCachedMovie(movieItem))));
        when(cacheValidatorMock.setCacheUpToDate()).thenReturn(Completable.never());

        moviesRepository.getMovies().test().assertNoErrors();

        verify(cacheValidatorMock).setCacheUpToDate();
    }

    private MovieItem createMovieItem() {
        return MovieItem.builder()
                .year("2084")
                .title("Robotron")
                .poster("image.png")
                .id(2)
                .genre("Sci-Fi")
                .build();
    }

    private MoviesResponse createMoviesResponse(List<MovieItem> movieItems) {
        return MoviesResponse.builder()
                .data(movieItems)
                .build();
    }

    private CachedMovie createCachedMovie(MovieItem movieItem) {
        return new CachedMovie(movieItem.id(),
                movieItem.year(),
                movieItem.genre(),
                movieItem.title(),
                movieItem.poster());
    }
}