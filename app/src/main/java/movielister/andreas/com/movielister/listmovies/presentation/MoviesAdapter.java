package movielister.andreas.com.movielister.listmovies.presentation;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import movielister.andreas.com.movielister.R;
import movielister.andreas.com.movielister.core.presentation.GlideApp;
import movielister.andreas.com.movielister.listmovies.domain.Movie;

import static java.util.Collections.emptyList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private List<Movie> movies = emptyList();

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewHolderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MoviesViewHolder(viewHolderView);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    void setData(List<Movie> newMovies) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MoviesCallback(movies, newMovies), true);
        movies = newMovies;
        diffResult.dispatchUpdatesTo(this);
    }

    static class MoviesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.itemMovieGenreText)
        TextView movieGenreText;
        @BindView(R.id.itemMovieImage)
        ImageView movieImage;

        MoviesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Movie movie) {
            GlideApp.with(itemView.getContext())
                    .load(movie.movieImage())
                    .placeholder(R.drawable.movie_placeholder)
                    .into(movieImage);

            movieGenreText.setText(movie.movieGenre());
        }
    }
}
