package movielister.andreas.com.movielister.listmovies.data;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class RetrofitApiGenerator implements ApiGenerator {

    private final Retrofit retrofit;

    RetrofitApiGenerator(String baseUrl, Gson gson) {
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public <T> T createService(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }

}
