package movielister.andreas.com.movielister.listmovies.domain;

import android.support.annotation.NonNull;

public interface StringMatcher {

    boolean containsAny(@NonNull String[] inputs, @NonNull String toMatch);

}
