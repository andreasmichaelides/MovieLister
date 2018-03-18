package movielister.andreas.com.movielister.listmovies.domain;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public class SortAlphabeticallyAscending {

    Single<List<Movie>> execute(List<Movie> movies) {
        return Observable.fromIterable(movies)
                .sorted((movie1, movie2) -> movie1.title().compareTo(movie2.title()))
                .toList();
    }

}
