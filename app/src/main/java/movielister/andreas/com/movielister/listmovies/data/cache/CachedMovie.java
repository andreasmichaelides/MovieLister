package movielister.andreas.com.movielister.listmovies.data.cache;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class CachedMovie {

    @PrimaryKey
    private int id;
    @NonNull
    private String year;
    @NonNull
    private String genre;
    @NonNull
    private String title;
    @NonNull
    private String poster;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CachedMovie(int id, @NonNull String year, @NonNull String genre, @NonNull String title, @NonNull String poster) {
        this.id = id;
        this.year = year;
        this.genre = genre;
        this.title = title;
        this.poster = poster;
    }

    @NonNull
    public String getYear() {
        return year;
    }

    public void setYear(@NonNull String year) {
        this.year = year;
    }

    @NonNull
    public String getGenre() {
        return genre;
    }

    public void setGenre(@NonNull String genre) {
        this.genre = genre;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getPoster() {
        return poster;
    }

    public void setPoster(@NonNull String poster) {
        this.poster = poster;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CachedMovie that = (CachedMovie) o;

        return id == that.id
                && year.equals(that.year)
                && genre.equals(that.genre)
                && title.equals(that.title)
                && poster.equals(that.poster);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + year.hashCode();
        result = 31 * result + genre.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + poster.hashCode();
        return result;
    }
}
