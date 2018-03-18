package movielister.andreas.com.movielister.listmovies.data;

public interface ApiGenerator {

    <T> T createService(Class<T> serviceClass);

}
