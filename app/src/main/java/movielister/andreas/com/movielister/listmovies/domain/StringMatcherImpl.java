package movielister.andreas.com.movielister.listmovies.domain;

import android.support.annotation.NonNull;

import com.annimon.stream.Stream;

public class StringMatcherImpl implements StringMatcher {

    @Override
    public boolean containsAny(@NonNull String[] inputs, @NonNull String toMatch) {
        return Stream.of(inputs)
                .anyMatch(input -> input.toLowerCase().contains(toMatch));
    }

}
