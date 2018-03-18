package movielister.andreas.com.movielister.listmovies.data;

import java.util.List;

import io.reactivex.Observable;
import movielister.andreas.com.movielister.core.SchedulersProvider;

class MoviesRepositoryImpl implements MoviesRepository {

    private final MoviesApi moviesApi;
    private final SchedulersProvider schedulersProvider;

    MoviesRepositoryImpl(MoviesApi moviesApi, SchedulersProvider schedulersProvider) {
        this.moviesApi = moviesApi;
        this.schedulersProvider = schedulersProvider;
    }

    @Override
    public Observable<List<MovieItem>> getMovies() {
        return moviesApi.getMovies()
                .map(MoviesResponse::data)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.mainThread())
                .toObservable();
    }
}
