package movielister.andreas.com.movielister.core.presentation;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjectionModule;
import movielister.andreas.com.movielister.core.Logger;
import movielister.andreas.com.movielister.core.LoggerImpl;
import movielister.andreas.com.movielister.core.SchedulersProvider;
import movielister.andreas.com.movielister.core.SchedulersProviderImpl;
import movielister.andreas.com.movielister.core.data.LocalStorage;
import movielister.andreas.com.movielister.core.data.LocalStorageImpl;
import movielister.andreas.com.movielister.listmovies.presentation.ListMoviesActivity;
import movielister.andreas.com.movielister.listmovies.presentation.ListMoviesModule;

@Module(includes = AndroidInjectionModule.class)
abstract class ApplicationModule {

    @Provides
    @Singleton
    static Context provideContext(Application application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    static Resources provideResources(Context context) {
        return context.getResources();
    }

    @Provides
    @Singleton
    static SchedulersProvider provideSchedulersProvider() {
        return new SchedulersProviderImpl();
    }

    @Provides
    @Singleton
    static LocalStorage provideLocalStorage(Context context) {
        return LocalStorageImpl.create(context);
    }

    @Provides
    @Singleton
    static Logger provideLogger() {
        return new LoggerImpl();
    }

    @ActivityScope
    @dagger.android.ContributesAndroidInjector(modules = ListMoviesModule.class)
    public abstract ListMoviesActivity listMoviesActivityInjector();
}
