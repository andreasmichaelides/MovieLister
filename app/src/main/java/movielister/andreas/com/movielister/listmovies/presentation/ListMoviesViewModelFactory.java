package movielister.andreas.com.movielister.listmovies.presentation;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.lang.Class;
import java.lang.Override;

import movielister.andreas.com.movielister.core.Logger;
import movielister.andreas.com.movielister.listmovies.domain.FilterMovies;
import movielister.andreas.com.movielister.listmovies.domain.GetMovies;

class ListMoviesViewModelFactory implements ViewModelProvider.Factory {

    private final GetMovies getMovies;
    private final FilterMovies filterMovies;
    private final Logger logger;

    ListMoviesViewModelFactory(GetMovies getMovies, FilterMovies filterMovies, Logger logger) {
        this.getMovies = getMovies;
        this.filterMovies = filterMovies;
        this.logger = logger;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ListMoviesViewModel.class)) {
            return (T) new ListMoviesViewModel(getMovies, filterMovies, logger);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
