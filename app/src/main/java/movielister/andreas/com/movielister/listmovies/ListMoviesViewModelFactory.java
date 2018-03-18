package movielister.andreas.com.movielister.listmovies;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.lang.Class;
import java.lang.Override;

import movielister.andreas.com.movielister.listmovies.domain.FilterMovies;
import movielister.andreas.com.movielister.listmovies.domain.GetMovies;

class ListMoviesViewModelFactory implements ViewModelProvider.Factory {

    private final GetMovies getMovies;
    private final FilterMovies filterMovies;

    ListMoviesViewModelFactory(GetMovies getMovies, FilterMovies filterMovies) {
        this.getMovies = getMovies;
        this.filterMovies = filterMovies;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ListMoviesViewModel.class)) {
            return (T) new ListMoviesViewModel(getMovies, filterMovies);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
