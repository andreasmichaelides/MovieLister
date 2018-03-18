package movielister.andreas.com.movielister.listmovies.data;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.List;

@AutoValue
public abstract class MoviesResponse {

    public abstract List<MovieItem> data();

    public static TypeAdapter<MoviesResponse> typeAdapter(Gson gson) {
        return new AutoValue_MoviesResponse.GsonTypeAdapter(gson);
    }
}