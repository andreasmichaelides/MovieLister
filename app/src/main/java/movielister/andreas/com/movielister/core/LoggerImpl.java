package movielister.andreas.com.movielister.core;

import android.support.annotation.NonNull;
import android.util.Log;

import javax.annotation.Nonnull;

public class LoggerImpl implements Logger {

    private static final String TAG = "MOVIE_LISTER";

    @Override
    public void d(@Nonnull Object caller, @NonNull String message) {
        Log.d(getNameOfCaller(caller), message);
    }

    @Override
    public void e(@Nonnull Object caller, Throwable throwable) {
        Log.e(getNameOfCaller(caller), String.format("%1$s - %2$s", TAG, getMessage(throwable)));
    }

    private String getNameOfCaller(@Nonnull Object caller) {
        return caller.getClass().getSimpleName();
    }

    private String getMessage(Throwable throwable) {
        String message;
        if (throwable != null) {
            message = throwable.getMessage() != null ? throwable.getMessage() : "No message in throwable";
            throwable.printStackTrace();
        } else {
            message = "Throwable null";
        }
        return message;
    }
}
