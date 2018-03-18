package movielister.andreas.com.movielister.listmovies.data;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

@GsonTypeAdapterFactory
abstract class AdapterFactory implements TypeAdapterFactory {
    static TypeAdapterFactory create() {
        return new AutoValueGson_AdapterFactory();
    }
}
