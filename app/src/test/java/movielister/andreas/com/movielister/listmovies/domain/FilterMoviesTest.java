package movielister.andreas.com.movielister.listmovies.domain;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import movielister.andreas.com.movielister.core.SchedulersProvider;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;

public class FilterMoviesTest {

    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();

    @Mock
    private StringMatcher stringMatcherMock;
    @Mock
    private SchedulersProvider schedulersProviderMock;

    private FilterMovies filterMovies;

    @Before
    public void setup() {
        when(schedulersProviderMock.computation()).thenReturn(Schedulers.trampoline());
        when(schedulersProviderMock.mainThread()).thenReturn(Schedulers.trampoline());

        filterMovies = new FilterMovies(0, stringMatcherMock, schedulersProviderMock);
    }

    @Test
    public void matchesStringTest() throws Exception {
        Movie movie = createMovie();
        when(stringMatcherMock.containsAny(new String[]{movie.movieGenre(), movie.title()}, "The")).thenReturn(true);

        TestObserver<List<Movie>> test = filterMovies.execute(Observable.just("The"), Observable.just(singletonList(movie))).test();

        test.assertValue(singletonList(movie))
                .assertNoErrors();
    }

    @Test
    public void noMatchTest() throws Exception {
        Movie movie = createMovie();
        when(stringMatcherMock.containsAny(new String[]{movie.movieGenre(), movie.title()}, "The")).thenReturn(false);

        TestObserver<List<Movie>> test = filterMovies.execute(Observable.just("The"), Observable.just(singletonList(movie))).test();

        test.assertValue(emptyList())
                .assertNoErrors();
    }

    private Movie createMovie() {
        return Movie.builder()
                .title("The Matrix")
                .movieImage("image.png")
                .movieGenre("Sci-Fi")
                .build();
    }
}