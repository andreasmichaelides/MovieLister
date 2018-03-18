package movielister.andreas.com.movielister.listmovies.domain;

import android.support.annotation.NonNull;

import com.annimon.stream.Stream;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import movielister.andreas.com.movielister.core.SchedulersProvider;

public class FilterMovies {

    private final long filterThrottleTimeout;
    private final StringMatcher stringMatcher;
    private final SchedulersProvider schedulersProvider;

    public FilterMovies(long filterThrottleTimeout, StringMatcher stringMatcher, SchedulersProvider schedulersProvider) {
        this.filterThrottleTimeout = filterThrottleTimeout;
        this.stringMatcher = stringMatcher;
        this.schedulersProvider = schedulersProvider;
    }

    public Observable<List<Movie>> execute(@NonNull final Observable<String> movieFilter,
                                    @NonNull final Observable<List<Movie>> movies) {
        Observable<String> throttledFilter = movieFilter.throttleWithTimeout(filterThrottleTimeout, TimeUnit.MILLISECONDS);

        return Observable.combineLatest(movies, throttledFilter, this::filterMovies)
                .onErrorResumeNext(Observable.empty())
                .subscribeOn(schedulersProvider.computation())
                .observeOn(schedulersProvider.mainThread());
    }

    private List<Movie> filterMovies(List<Movie> movies, String filter) {
        return filter.isEmpty()
                ? movies
                : Stream.of(movies)
                .filter(movie -> stringMatcher.containsAny(new String[]{movie.movieGenre(), movie.title()}, filter))
                .toList();
    }

}
