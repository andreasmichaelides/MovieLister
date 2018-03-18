package movielister.andreas.com.movielister.listmovies.domain;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import movielister.andreas.com.movielister.listmovies.data.MovieItem;
import movielister.andreas.com.movielister.listmovies.data.MoviesRepository;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;

public class GetMoviesTest {

    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();

    @Mock
    private MoviesRepository moviesRepositoryMock;
    @Mock
    private MovieItemToMovieMapper movieItemToMovieMapper;
    @Mock
    private SortAlphabeticallyAscending sortAlphabeticallyAscendingMock;

    @InjectMocks
    private GetMovies getMovies;

    @Test
    public void getMoviesTest() throws Exception {
        MovieItem movieItem = createMovieItem();
        Movie movie = createMovie();
        when(moviesRepositoryMock.getMovies()).thenReturn(Observable.just(singletonList(movieItem)));
        when(movieItemToMovieMapper.mapToMovies(singletonList(movieItem))).thenReturn(Single.just(singletonList(movie)));
        when(sortAlphabeticallyAscendingMock.execute(singletonList(movie))).thenReturn(Single.just(singletonList(movie)));

        TestObserver<List<Movie>> test = getMovies.execute().test();

        test.assertValue(singletonList(movie))
                .assertNoErrors();
    }

    @Test
    public void getMoviesEmitsErrorIfNoValueTest() throws Exception {
        MovieItem movieItem = createMovieItem();
        Movie movie = createMovie();
        when(moviesRepositoryMock.getMovies()).thenReturn(Observable.just(singletonList(movieItem)));
        when(movieItemToMovieMapper.mapToMovies(singletonList(movieItem))).thenReturn(Single.just(singletonList(movie)));
        when(sortAlphabeticallyAscendingMock.execute(singletonList(movie))).thenReturn(Single.never());

        TestObserver<List<Movie>> test = getMovies.execute().test();

        test.assertNoErrors();
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

    private Movie createMovie() {
        return Movie.builder()
                .title("Robotron")
                .movieImage("image.png")
                .movieGenre("Sci-Fi")
                .build();
    }
}