package movielister.andreas.com.movielister.listmovies.data;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import movielister.andreas.com.movielister.listmovies.data.cache.CachedMovie;

class MovieItemsToCachedMoviesMapper {

    Single<List<CachedMovie>> mapToCachedMovies(List<MovieItem> movieItems) {
        return Observable.fromIterable(movieItems)
                .map(this::mapToCachedMovie)
                .toList();
    }

    private CachedMovie mapToCachedMovie(MovieItem movieItem) {
        return new CachedMovie(movieItem.id(),
                movieItem.year(),
                movieItem.genre(),
                movieItem.title(),
                movieItem.poster());
    }

}
