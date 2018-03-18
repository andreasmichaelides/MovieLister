package movielister.andreas.com.movielister.listmovies.data;

import java.util.List;

import io.reactivex.Observable;

public interface MoviesRepository {

    Observable<List<MovieItem>> getMovies();

}
