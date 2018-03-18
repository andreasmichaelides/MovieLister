package movielister.andreas.com.movielister.core.data;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalStorageImpl implements LocalStorage {

    private final String KEY_MOVIE_CACHE_TIME = "KEY_MOVIE_CACHE_TIME";
    private final SharedPreferences sharedPreferences;

    public static LocalStorageImpl create(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MovieListerSharedPreferences", Context.MODE_PRIVATE);
        return new LocalStorageImpl(sharedPreferences);
    }

    private LocalStorageImpl(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public long getMovieCacheTime() {
        return sharedPreferences.getLong(KEY_MOVIE_CACHE_TIME, 0);
    }

    @Override
    public void setMovieCacheTime(long time) {
        sharedPreferences.edit()
                .putLong(KEY_MOVIE_CACHE_TIME, time)
                .apply();
    }
}
