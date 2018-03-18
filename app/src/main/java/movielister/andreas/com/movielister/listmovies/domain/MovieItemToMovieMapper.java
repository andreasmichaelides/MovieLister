package movielister.andreas.com.movielister.listmovies.domain;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import movielister.andreas.com.movielister.listmovies.data.MovieItem;

public class MovieItemToMovieMapper {

    Single<List<Movie>> mapToMovies(List<MovieItem> movieItems) {
        return Observable.fromIterable(movieItems)
                .map(this::mapToMovie)
                .toList();
    }

    private Movie mapToMovie(MovieItem movieItem) {
        return Movie.builder()
                .movieGenre(movieItem.genre())
                .title(movieItem.title())
                .movieImage(movieItem.poster())
                .build();
    }

}
