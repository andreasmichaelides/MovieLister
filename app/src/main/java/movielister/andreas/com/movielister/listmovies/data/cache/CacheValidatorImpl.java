package movielister.andreas.com.movielister.listmovies.data.cache;

import java.util.Calendar;
import java.util.GregorianCalendar;

import io.reactivex.Completable;
import io.reactivex.Observable;
import movielister.andreas.com.movielister.core.data.LocalStorage;

class CacheValidatorImpl implements CacheValidator {

    private final long cacheExpiryDuration;
    private final LocalStorage localStorage;
    private final TimeProvider timeProvider;

    CacheValidatorImpl(long cacheExpiryDuration, LocalStorage localStorage, TimeProvider timeProvider) {
        this.cacheExpiryDuration = cacheExpiryDuration;
        this.localStorage = localStorage;
        this.timeProvider = timeProvider;
    }

    @Override
    public Completable setCacheUpToDate() {
        return Completable.fromAction(() -> localStorage.setMovieCacheTime(timeProvider.getCurrentTimeInMillis()));
    }

    @Override
    public Observable<Boolean> isCacheValid() {
        return Observable.just(localStorage.getMovieCacheTime())
                .map(cachedTime -> cachedTime + cacheExpiryDuration)
                .map(expiryTime -> timeProvider.getCurrentTimeInMillis() >= expiryTime);
    }

    @Override
    public String toString() {
        Calendar expiryCalendar = GregorianCalendar.getInstance();
        expiryCalendar.setTimeInMillis(localStorage.getMovieCacheTime() + cacheExpiryDuration);
        return "Cache expiry at: " + expiryCalendar.getTime();
    }
}
