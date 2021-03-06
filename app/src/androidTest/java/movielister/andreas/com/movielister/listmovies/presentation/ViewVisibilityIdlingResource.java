package movielister.andreas.com.movielister.listmovies.presentation;

import android.app.Activity;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.test.espresso.*;
import android.view.View;

import java.lang.ref.WeakReference;

// Another class that helps to idle a test, until a view's visibility is set as required

/**
 * {@link IdlingResource} which monitors a {@link View} for a given visibility state. The resource is considered idle when the
 * View has the desired state.
 *
 * @author vaughandroid@gmail.com
 */
public class ViewVisibilityIdlingResource implements IdlingResource {

    private static final int IDLE_POLL_DELAY_MILLIS = 100;

    /** Hold weak reference to the View, so we don't leak memory even if the resource isn't unregistered. */
    private final WeakReference<View> mView;
    private final int mVisibility;
    private final String mName;

    private ResourceCallback mResourceCallback;

    /**
     * @param activity which owns the View
     * @param viewId ID of the View to monitor
     * @param visibility One of {@link View#VISIBLE}, {@link View#INVISIBLE}, or {@link View#GONE}.
     */
    ViewVisibilityIdlingResource(@NonNull Activity activity, @IdRes int viewId, int visibility) {
        this(activity.findViewById(viewId), visibility);
    }

    /**
     * @param view the View to monitor
     * @param visibility One of {@link View#VISIBLE}, {@link View#INVISIBLE}, or {@link View#GONE}.
     */
    private ViewVisibilityIdlingResource(@NonNull View view, int visibility) {
        mView = new WeakReference<View>(view);
        mVisibility = visibility;
        mName = "View Visibility for view " + view.getId() + "(@" + System.identityHashCode(mView) + ")";
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public boolean isIdleNow() {
        View view = mView.get();
        final boolean isIdle = view == null || view.getVisibility() == mVisibility;
        if (isIdle) {
            if (mResourceCallback != null) {
                mResourceCallback.onTransitionToIdle();
            }
        } else {
            /* Force a re-check of the idle state in a little while.
             * If isIdleNow() returns false, Espresso only polls it every few seconds which can slow down our tests.
             * Ideally we would watch for the visibility state changing, but AFAIK we can't detect when a View's
             * visibility changes to GONE.
             */
            new Handler().postDelayed(this::isIdleNow, IDLE_POLL_DELAY_MILLIS);
        }

        return isIdle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        mResourceCallback = resourceCallback;
    }
}
