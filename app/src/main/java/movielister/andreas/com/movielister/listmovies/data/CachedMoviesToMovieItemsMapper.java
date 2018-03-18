package movielister.andreas.com.movielister.listmovies.data;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import movielister.andreas.com.movielister.listmovies.data.cache.CachedMovie;

public class CachedMoviesToMovieItemsMapper {

    Single<List<MovieItem>> mapToMovieItems(List<CachedMovie> cachedMovies) {
        return Observable.fromIterable(cachedMovies)
                .map(this::mapToMovieItem)
                .toList();
    }

    private MovieItem mapToMovieItem(CachedMovie cachedMovie) {
        return MovieItem.builder()
                .genre(cachedMovie.getGenre())
                .id(cachedMovie.getId())
                .poster(cachedMovie.getPoster())
                .title(cachedMovie.getTitle())
                .year(cachedMovie.getYear())
                .build();
    }

}
