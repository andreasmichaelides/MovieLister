package movielister.andreas.com.movielister.listmovies.presentation;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import movielister.andreas.com.movielister.core.Logger;
import movielister.andreas.com.movielister.listmovies.domain.FilterMovies;
import movielister.andreas.com.movielister.listmovies.domain.GetMovies;
import movielister.andreas.com.movielister.listmovies.domain.Movie;

class ListMoviesViewModel extends ViewModel {

    private final Subject<Object> loadMovies = BehaviorSubject.create();
    private final Subject<List<Movie>> loadedMovies = BehaviorSubject.create();
    private final Subject<String> moviesFilter = BehaviorSubject.createDefault("");
    private final CompositeDisposable subscriptions = new CompositeDisposable();

    private final MutableLiveData<List<Movie>> filteredMovies = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<Boolean> showError = new MutableLiveData<>();

    ListMoviesViewModel(GetMovies getMovies, FilterMovies filterMovies, Logger logger) {
        subscriptions.addAll(
                loadMovies.flatMapSingle(ignored -> getMovies.execute()
                        .doOnSubscribe(disposable -> onMovieLoadingStarted())
                        .doOnError(throwable -> onMovieLoadingError())
                        .doOnError(throwable -> logger.e(this, throwable))
                        .onErrorResumeNext(Single.never()))
                        .subscribe(this::onMoviesLoaded, throwable -> logger.e(this, throwable)),
                filterMovies.execute(moviesFilter, loadedMovies)
                        .doOnError(throwable -> logger.e(this, throwable))
                        .onErrorResumeNext(Observable.empty())
                        .subscribe(filteredMovies::setValue, throwable -> logger.e(this, throwable))
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        subscriptions.clear();
    }

    private void onMovieLoadingStarted() {
        isLoading.setValue(true);
        showError.setValue(false);
    }

    private void onMovieLoadingError() {
        isLoading.setValue(false);
        showError.setValue(true);
    }

    private void onMoviesLoaded(List<Movie> movies) {
        isLoading.setValue(false);
        loadedMovies.onNext(movies);
    }

    void loadMovies() {
        loadMovies.onNext(new Object());
    }

    void filterMovies(String filter) {
        moviesFilter.onNext(filter);
    }

    LiveData<Boolean> isLoading() {
        return isLoading;
    }

    LiveData<Boolean> showError() {
        return showError;
    }

    LiveData<List<Movie>> movies() {
        return filteredMovies;
    }

}
