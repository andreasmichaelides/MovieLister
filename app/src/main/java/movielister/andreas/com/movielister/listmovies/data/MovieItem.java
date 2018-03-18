package movielister.andreas.com.movielister.listmovies.data;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class MovieItem {

    public abstract String year();

    public abstract String genre();

    public abstract int id();

    public abstract String title();

    public abstract String poster();

    public static TypeAdapter<MovieItem> typeAdapter(Gson gson) {
        return new AutoValue_MovieItem.GsonTypeAdapter(gson);
    }
}