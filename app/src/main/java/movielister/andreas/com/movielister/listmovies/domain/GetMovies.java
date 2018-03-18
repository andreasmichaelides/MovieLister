package movielister.andreas.com.movielister.listmovies.domain;

import java.util.List;

import io.reactivex.Single;
import movielister.andreas.com.movielister.listmovies.data.MoviesRepository;

public class GetMovies {

    private final MoviesRepository moviesRepository;
    private final MovieItemToMovieMapper movieItemToMovieMapper;
    private final SortAlphabeticallyAscending sortAlphabeticallyAscending;

    public GetMovies(MoviesRepository moviesRepository,
                     MovieItemToMovieMapper movieItemToMovieMapper,
                     SortAlphabeticallyAscending sortAlphabeticallyAscending) {
        this.moviesRepository = moviesRepository;
        this.movieItemToMovieMapper = movieItemToMovieMapper;
        this.sortAlphabeticallyAscending = sortAlphabeticallyAscending;
    }

    public Single<List<Movie>> execute() {
        return moviesRepository.getMovies()
                .flatMapSingle(movieItemToMovieMapper::mapToMovies)
                .flatMapSingle(sortAlphabeticallyAscending::execute)
                .firstOrError();
    }

}
