package movielister.andreas.com.movielister.listmovies.domain;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Movie {

    public abstract String movieImage();

    public abstract String movieGenre();

    static Builder builder() {
        return new AutoValue_Movie.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder movieImage(String movieImage);

        public abstract Builder movieGenre(String movieGenre);

        public abstract Movie build();
    }
}
