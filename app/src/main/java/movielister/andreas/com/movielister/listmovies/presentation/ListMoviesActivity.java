package movielister.andreas.com.movielister.listmovies.presentation;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.ProgressBar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import movielister.andreas.com.movielister.R;
import movielister.andreas.com.movielister.listmovies.domain.Movie;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ListMoviesActivity extends DaggerAppCompatActivity {

    @Inject
    ListMoviesViewModelFactory viewModelFactory;

    @BindView(R.id.moviesFilter)
    SearchView moviesFilter;
    @BindView(R.id.moviesRecyclerView)
    RecyclerView moviesRecyclerView;
    @BindView(R.id.moviesProgressBar)
    ProgressBar moviesProgressBar;

    private ListMoviesViewModel viewModel;
    private final MoviesAdapter moviesAdapter = new MoviesAdapter();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_movies);
        ButterKnife.bind(this);

        moviesRecyclerView.setAdapter(moviesAdapter);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListMoviesViewModel.class);
        viewModel.movies().observe(this, this::onMoviesLoaded);
        viewModel.isLoading().observe(this, this::onLoadingChanged);
        viewModel.loadMovies();

        moviesFilter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String filter) {
                viewModel.filterMovies(filter);
                return true;
            }
        });
    }

    private void onLoadingChanged(Boolean isLoading) {
        moviesProgressBar.setVisibility(isLoading ? VISIBLE : GONE);
    }

    private void onMoviesLoaded(List<Movie> movies) {
        moviesAdapter.setData(movies);
    }
}
