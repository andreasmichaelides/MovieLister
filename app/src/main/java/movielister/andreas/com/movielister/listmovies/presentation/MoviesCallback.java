package movielister.andreas.com.movielister.listmovies.presentation;

import android.support.v7.util.DiffUtil;

import java.util.List;

import movielister.andreas.com.movielister.listmovies.domain.Movie;

public class MoviesCallback extends DiffUtil.Callback {

    private final List<Movie> oldMovies;
    private final List<Movie> newMovies;

    public MoviesCallback(List<Movie> oldMovies, List<Movie> newMovies) {
        this.oldMovies = oldMovies;
        this.newMovies = newMovies;
    }

    @Override
    public int getOldListSize() {
        return oldMovies.size();
    }

    @Override
    public int getNewListSize() {
        return newMovies.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldMovies.get(oldItemPosition).title().equals(newMovies.get(newItemPosition).title());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldMovies.get(oldItemPosition).equals(newMovies.get(newItemPosition));
    }
}
