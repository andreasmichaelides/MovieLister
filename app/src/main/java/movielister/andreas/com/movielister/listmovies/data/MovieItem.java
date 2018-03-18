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

    static TypeAdapter<MovieItem> typeAdapter(Gson gson) {
        return new AutoValue_MovieItem.GsonTypeAdapter(gson);
    }

    public static Builder builder() {
        return new AutoValue_MovieItem.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder year(String year);

        public abstract Builder genre(String genre);

        public abstract Builder id(int id);

        public abstract Builder title(String title);

        public abstract Builder poster(String poster);

        public abstract MovieItem build();
    }
}