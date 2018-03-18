package movielister.andreas.com.movielister.core;

import android.support.annotation.NonNull;

import javax.annotation.Nonnull;

public interface Logger {

    void d(@Nonnull Object caller, @NonNull String message);

    void e(@Nonnull Object caller, Throwable throwable);

}
