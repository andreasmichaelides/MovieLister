package movielister.andreas.com.movielister.listmovies.presentation;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import movielister.andreas.com.movielister.core.Logger;
import movielister.andreas.com.movielister.listmovies.domain.FilterMovies;
import movielister.andreas.com.movielister.listmovies.domain.GetMovies;
import movielister.andreas.com.movielister.listmovies.domain.Movie;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ListMoviesViewModelTest {

    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Mock
    private GetMovies getMoviesMock;
    @Mock
    private FilterMovies filterMoviesMock;
    @Mock
    private Logger logger;

    private ListMoviesViewModel listMoviesViewModel;

    @Test
    public void moviesAreLoaded() throws Exception {
        List<Movie> expectedList = singletonList(createMovie());
        when(getMoviesMock.execute()).thenReturn(Single.just(expectedList));
        when(filterMoviesMock.execute(any(), any())).thenReturn(Observable.just(expectedList));
        listMoviesViewModel = new ListMoviesViewModel(getMoviesMock, filterMoviesMock, logger);

        listMoviesViewModel.loadMovies();

        assertEquals(expectedList, listMoviesViewModel.movies().getValue());
    }

    @Test
    public void loadedMoviesHideLoading() throws Exception {
        List<Movie> expectedList = singletonList(createMovie());
        when(getMoviesMock.execute()).thenReturn(Single.just(expectedList));
        when(filterMoviesMock.execute(any(), any())).thenReturn(Observable.just(expectedList));
        listMoviesViewModel = new ListMoviesViewModel(getMoviesMock, filterMoviesMock, logger);

        listMoviesViewModel.loadMovies();

        assertEquals(false, listMoviesViewModel.isLoading().getValue());
    }

    @Test
    public void loadingMoviesShowsLoading() throws Exception {
        when(getMoviesMock.execute()).thenReturn(Single.never());
        when(filterMoviesMock.execute(any(), any())).thenReturn(Observable.empty());
        listMoviesViewModel = new ListMoviesViewModel(getMoviesMock, filterMoviesMock, logger);

        listMoviesViewModel.loadMovies();

        assertEquals(true, listMoviesViewModel.isLoading().getValue());
    }

    @Test
    public void loadingMoviesHidesErrorMessage() throws Exception {
        when(getMoviesMock.execute()).thenReturn(Single.never());
        when(filterMoviesMock.execute(any(), any())).thenReturn(Observable.empty());
        listMoviesViewModel = new ListMoviesViewModel(getMoviesMock, filterMoviesMock, logger);

        listMoviesViewModel.loadMovies();

        assertEquals(false, listMoviesViewModel.showError().getValue());
    }

    @Test
    public void loadingMoviesErrorShowsError() throws Exception {
        when(getMoviesMock.execute()).thenReturn(Single.error(new Throwable("Not able to get movies")));
        when(filterMoviesMock.execute(any(), any())).thenReturn(Observable.empty());
        listMoviesViewModel = new ListMoviesViewModel(getMoviesMock, filterMoviesMock, logger);

        listMoviesViewModel.loadMovies();

        assertEquals(true, listMoviesViewModel.showError().getValue());
    }

    private Movie createMovie() {
        return Movie.builder()
                .title("The Matrix")
                .movieImage("image.png")
                .movieGenre("Sci-Fi")
                .build();
    }
}