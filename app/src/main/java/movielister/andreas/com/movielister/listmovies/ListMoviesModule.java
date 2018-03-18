package movielister.andreas.com.movielister.listmovies;

import dagger.Module;
import dagger.Provides;
import movielister.andreas.com.movielister.core.presentation.ActivityScope;
import movielister.andreas.com.movielister.listmovies.data.MoviesDataModule;
import movielister.andreas.com.movielister.listmovies.data.MoviesRepository;
import movielister.andreas.com.movielister.listmovies.domain.GetMovies;
import movielister.andreas.com.movielister.listmovies.domain.MovieItemToMovieMapper;

@Module(includes = MoviesDataModule.class)
public abstract class ListMoviesModule {

    @Provides
    @ActivityScope
    static MovieItemToMovieMapper provideMovieItemToMovieMapper() {
        return new MovieItemToMovieMapper();
    }

    @Provides
    @ActivityScope
    static GetMovies provideGetMovies(MoviesRepository moviesRepository, MovieItemToMovieMapper movieItemToMovieMapper) {
        return new GetMovies(moviesRepository, movieItemToMovieMapper);
    }

    @Provides
    @ActivityScope
    static ListMoviesViewModelFactory provideListMovies(GetMovies getMovies) {
        return new ListMoviesViewModelFactory(getMovies);
    }
}
