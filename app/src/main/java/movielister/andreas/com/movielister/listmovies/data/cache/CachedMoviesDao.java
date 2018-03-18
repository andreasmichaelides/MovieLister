package movielister.andreas.com.movielister.listmovies.data.cache;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public abstract class CachedMoviesDao {

    @Transaction
    public void updateCache(List<CachedMovie> movies) {
        deleteAllMovies();
        insertMovies(movies);
    }

    @Query("select * from CachedMovie")
    public abstract Flowable<List<CachedMovie>> getCachedMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertMovies(List<CachedMovie> movies);

    @Query("DELETE FROM CachedMovie")
    public abstract void deleteAllMovies();

}
