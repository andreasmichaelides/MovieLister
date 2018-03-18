package movielister.andreas.com.movielister.listmovies.data.cache;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface CacheValidator {

    Observable<Boolean> isCacheValid();

    Completable setCacheUpToDate();

}
