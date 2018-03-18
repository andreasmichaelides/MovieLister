package movielister.andreas.com.movielister.listmovies.data;

import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import movielister.andreas.com.movielister.R;
import movielister.andreas.com.movielister.core.SchedulersProvider;

@Module
public abstract class MoviesDataModule {

    @Provides
    static Gson provideGson() {
        return new GsonBuilder().
                registerTypeAdapterFactory(AdapterFactory.create())
                .create();
    }

    @Provides
    static ApiGenerator provideRetrofitApiGenerator(Resources resources, Gson gson) {
        return new RetrofitApiGenerator(resources.getString(R.string.movies_api_base_url), gson);
    }

    @Provides
    static MoviesApi provideMoviesApi(ApiGenerator apiGenerator) {
        return apiGenerator.createService(MoviesApi.class);
    }

    @Provides
    static MoviesRepository provideMoviesRepository(MoviesApi moviesApi, SchedulersProvider schedulersProvider) {
        return new MoviesRepositoryImpl(moviesApi, schedulersProvider);
    }
}
