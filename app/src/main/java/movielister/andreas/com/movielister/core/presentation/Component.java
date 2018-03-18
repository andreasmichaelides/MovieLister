package movielister.andreas.com.movielister.core.presentation;

public interface Component<T> {
    void inject(T objectToBeInjectedWithModules);
}
