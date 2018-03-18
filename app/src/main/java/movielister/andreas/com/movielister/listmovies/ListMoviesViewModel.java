package movielister.andreas.com.movielister.listmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import movielister.andreas.com.movielister.listmovies.domain.GetMovies;
import movielister.andreas.com.movielister.listmovies.domain.Movie;

class ListMoviesViewModel extends ViewModel {

    private final GetMovies getMovies;

    private final Subject<Object> loadMovies = BehaviorSubject.create();
    private final CompositeDisposable subscriptions = new CompositeDisposable();

    private final MutableLiveData<List<Movie>> loadedMovies = new MutableLiveData<>();

    ListMoviesViewModel(GetMovies getMovies) {
        this.getMovies = getMovies;
        subscriptions.addAll(
                loadMovies.flatMapSingle(ignored -> getMovies.execute())
                        .subscribe(loadedMovies::setValue)
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        subscriptions.clear();
    }

    void loadMovies() {
        loadMovies.onNext(new Object());
    }

    LiveData<List<Movie>> movies() {
        return loadedMovies;
    }
}
