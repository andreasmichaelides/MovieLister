package movielister.andreas.com.movielister.listmovies;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import movielister.andreas.com.movielister.R;
import movielister.andreas.com.movielister.listmovies.domain.Movie;

public class ListMoviesActivity extends DaggerAppCompatActivity {

    @Inject
    ListMoviesViewModelFactory viewModelFactory;

//    @BindView(R.id.moviesFilter)
//    SearchView moviesFilter;
    @BindView(R.id.moviesRecyclerView)
    RecyclerView moviesRecyclerView;

    private ListMoviesViewModel viewModel;
    private final MoviesAdapter moviesAdapter = new MoviesAdapter();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_movies);
        ButterKnife.bind(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        moviesRecyclerView.setAdapter(moviesAdapter);
//        moviesRecyclerView.setLayoutManager(gridLayoutManager);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListMoviesViewModel.class);

        viewModel.movies().observe(this, this::onMoviesLoaded);
        viewModel.loadMovies();
    }

    private void onMoviesLoaded(List<Movie> movies) {
        moviesAdapter.setData(movies);
    }
}
