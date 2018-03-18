package movielister.andreas.com.movielister.listmovies.data.cache;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface CachedMoviesDao {

    @Query("select * from CachedMovie")
    Flowable<List<CachedMovie>> getCachedMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void updateCache(List<CachedMovie> movies);

}
