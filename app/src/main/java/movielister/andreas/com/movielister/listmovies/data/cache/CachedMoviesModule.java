package movielister.andreas.com.movielister.listmovies.data.cache;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import movielister.andreas.com.movielister.core.data.LocalStorage;

@Module
public abstract class CachedMoviesModule {

    private final static String STRING_DB_NAME = "STRING_CACHED_MOVIES_DB_NAME";
    private final static String LONG_CACHE_EXPIRY_DURATION = "LONG_CACHE_EXPIRY_DURATION";

    @Provides
    @Named(LONG_CACHE_EXPIRY_DURATION)
    static long provideCacheExpiryDuration() {
        return 600000; // Ten minutes in millis
    }

    @Provides
    @Named(STRING_DB_NAME)
    static String provideCachedMoviesDbName() {
        return "CachedMoviesDB";
    }

    @Provides
    static CachedMoviesDao provideCachedMoviesDao(CachedMoviesDatabase cachedMoviesDatabase) {
        return cachedMoviesDatabase.cachedMoviesDao();
    }

    @Provides
    static CachedMoviesDatabase provideCachedMoviesDatabase(Context context, @Named(STRING_DB_NAME) String dbName) {
        return Room.databaseBuilder(context, CachedMoviesDatabase.class, dbName)
                .build();
    }

    @Provides
    static TimeProvider provideTimeProvider() {
        return new TimeProviderImpl();
    }

    @Provides
    static CacheValidator provideCacheValidator(LocalStorage localStorage,
                                                @Named(LONG_CACHE_EXPIRY_DURATION) long cacheExpiryDuration,
                                                TimeProvider timeProvider) {
        return new CacheValidatorImpl(cacheExpiryDuration, localStorage, timeProvider);
    }

}
