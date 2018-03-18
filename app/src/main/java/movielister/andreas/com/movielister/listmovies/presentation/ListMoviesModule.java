package movielister.andreas.com.movielister.listmovies.presentation;

import dagger.Module;
import dagger.Provides;
import movielister.andreas.com.movielister.core.Logger;
import movielister.andreas.com.movielister.core.SchedulersProvider;
import movielister.andreas.com.movielister.core.presentation.ActivityScope;
import movielister.andreas.com.movielister.listmovies.data.MoviesDataModule;
import movielister.andreas.com.movielister.listmovies.data.MoviesRepository;
import movielister.andreas.com.movielister.listmovies.domain.FilterMovies;
import movielister.andreas.com.movielister.listmovies.domain.GetMovies;
import movielister.andreas.com.movielister.listmovies.domain.MovieItemToMovieMapper;
import movielister.andreas.com.movielister.listmovies.domain.StringMatcher;
import movielister.andreas.com.movielister.listmovies.domain.StringMatcherImpl;

@Module(includes = MoviesDataModule.class)
public abstract class ListMoviesModule {

    private static final long FILTER_THROTTLE_TIMEOUT = 200;

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
    static StringMatcher provideStringMatcher() {
        return new StringMatcherImpl();
    }

    @Provides
    @ActivityScope
    static FilterMovies provideFilterMovies(StringMatcher stringMatcher, SchedulersProvider schedulersProvider) {
        return new FilterMovies(FILTER_THROTTLE_TIMEOUT, stringMatcher, schedulersProvider);
    }

    @Provides
    @ActivityScope
    static ListMoviesViewModelFactory provideListMovies(GetMovies getMovies, FilterMovies filterMovies, Logger logger) {
        return new ListMoviesViewModelFactory(getMovies, filterMovies, logger);
    }
}
