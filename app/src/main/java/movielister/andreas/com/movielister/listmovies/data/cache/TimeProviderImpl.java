package movielister.andreas.com.movielister.listmovies.data.cache;

class TimeProviderImpl implements TimeProvider {

    @Override
    public long getCurrentTimeInMillis() {
        return System.currentTimeMillis();
    }

}
