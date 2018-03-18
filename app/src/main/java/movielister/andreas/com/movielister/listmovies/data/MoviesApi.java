package movielister.andreas.com.movielister.listmovies.data;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface MoviesApi {

    @GET("movies")
    Single<MoviesResponse> getMovies();

}
