package movielister.andreas.com.movielister.core.presentation;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjectionModule;
import movielister.andreas.com.movielister.core.SchedulersProvider;
import movielister.andreas.com.movielister.core.SchedulersProviderImpl;
import movielister.andreas.com.movielister.listmovies.ListMoviesActivity;
import movielister.andreas.com.movielister.listmovies.ListMoviesModule;

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

    @ActivityScope
    @dagger.android.ContributesAndroidInjector(modules = ListMoviesModule.class)
    public abstract ListMoviesActivity listMoviesActivityInjector();
}
