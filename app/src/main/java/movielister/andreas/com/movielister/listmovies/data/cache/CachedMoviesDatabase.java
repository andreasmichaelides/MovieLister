package movielister.andreas.com.movielister.listmovies.data.cache;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = CachedMovie.class, version = 1)
public abstract class CachedMoviesDatabase extends RoomDatabase {

    public abstract CachedMoviesDao cachedMoviesDao();

}
