package movielister.andreas.com.movielister.listmovies.data.cache;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import io.reactivex.observers.TestObserver;

import static java.util.Collections.singletonList;

@RunWith(AndroidJUnit4.class)
public class CachedMoviesDatabaseTest {

    private CachedMoviesDatabase cachedMoviesDatabase;
    private CachedMoviesDao cachedMoviesDao;

    @Before
    public void setUp() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        cachedMoviesDatabase = Room.inMemoryDatabaseBuilder(appContext, CachedMoviesDatabase.class).build();
        cachedMoviesDao = cachedMoviesDatabase.cachedMoviesDao();
    }

    @After
    public void closeDb() throws Exception {
        cachedMoviesDatabase.close();
    }

    @Test
    public void updatingDatabaseShouldRemoveOldEntries() {
        List<CachedMovie> firstMovieList = singletonList(cachedMovie(1));
        List<CachedMovie> secondMovieList = Arrays.asList(cachedMovie(22), cachedMovie(222));
        cachedMoviesDao.insertMovies(firstMovieList);
        cachedMoviesDao.updateCache(secondMovieList);

        TestObserver<List<CachedMovie>> test = cachedMoviesDao.getCachedMovies().test();

        test.assertValue(secondMovieList)
                .assertNoErrors();
    }


    private CachedMovie cachedMovie(int movieId) {
        return new CachedMovie(movieId,
               "1986",
                "Animation",
                "A movie",
                "*.png");
    }
}