package movielister.andreas.com.movielister.listmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import movielister.andreas.com.movielister.listmovies.domain.FilterMovies;
import movielister.andreas.com.movielister.listmovies.domain.GetMovies;
import movielister.andreas.com.movielister.listmovies.domain.Movie;

class ListMoviesViewModel extends ViewModel {

    private final GetMovies getMovies;
    private final FilterMovies filterMovies;

    private final Subject<Object> loadMovies = BehaviorSubject.create();
    private final Subject<List<Movie>> loadedMovies = BehaviorSubject.create();
    private final Subject<String> moviesFilter = BehaviorSubject.createDefault("");
    private final CompositeDisposable subscriptions = new CompositeDisposable();

    private final MutableLiveData<List<Movie>> filteredMovies = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    ListMoviesViewModel(GetMovies getMovies, FilterMovies filterMovies) {
        this.getMovies = getMovies;
        this.filterMovies = filterMovies;
        subscriptions.addAll(
                loadMovies.flatMapSingle(ignored -> getMovies.execute()
                        .doOnSubscribe(disposable -> isLoading.setValue(true))
                        .doOnError(throwable -> isLoading.setValue(false)))
                        .subscribe(this::onMoviesLoaded),
                filterMovies.execute(moviesFilter, loadedMovies)
                        .subscribe(filteredMovies::setValue)
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        subscriptions.clear();
    }

    private void onMoviesLoaded(List<Movie> movies) {
        isLoading.setValue(false);
        loadedMovies.onNext(movies);
    }

    void loadMovies() {
        loadMovies.onNext(new Object());
    }

    LiveData<Boolean> isLoading() {
        return isLoading;
    }

    LiveData<List<Movie>> movies() {
        return filteredMovies;
    }

    void filterMovies(String filter) {
        moviesFilter.onNext(filter);
    }
}
