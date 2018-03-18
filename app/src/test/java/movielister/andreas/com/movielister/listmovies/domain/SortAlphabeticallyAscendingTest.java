package movielister.andreas.com.movielister.listmovies.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import io.reactivex.observers.TestObserver;

public class SortAlphabeticallyAscendingTest {

    private SortAlphabeticallyAscending sortAlphabeticallyAscending;

    @Before
    public void setUp() {
        sortAlphabeticallyAscending = new SortAlphabeticallyAscending();
    }

    @Test
    public void execute() throws Exception {
        List<Movie> movieList = Arrays.asList(createMovie("The Matrix"), createMovie("Batman"));
        List<Movie> expectedList = Arrays.asList(createMovie("Batman"), createMovie("The Matrix"));

        TestObserver<List<Movie>> test = sortAlphabeticallyAscending.execute(movieList).test();

        test.assertValue(expectedList)
                .assertNoErrors();
    }

    private Movie createMovie(String title) {
        return Movie.builder()
                .title(title)
                .movieImage("image.png")
                .movieGenre("Sci-Fi")
                .build();
    }
}