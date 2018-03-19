package movielister.andreas.com.movielister.listmovies.presentation;

import android.support.annotation.NonNull;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import movielister.andreas.com.movielister.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.internal.util.Checks.checkNotNull;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ListMoviesActivityTest {

    @Rule
    public ActivityTestRule<ListMoviesActivity> mActivityRule = new ActivityTestRule(ListMoviesActivity.class, true);

    @Test
    public void filteringMovieListTest() throws Exception {
        // Waiting for the progress bar's visibility to become gone, so the test can filter the list with data populated
        ViewVisibilityIdlingResource viewVisibilityIdlingResource = new ViewVisibilityIdlingResource(mActivityRule.getActivity(),
                R.id.moviesProgressBar,
                View.GONE);
        Espresso.registerIdlingResources(viewVisibilityIdlingResource);

        onView(withId(android.support.design.R.id.search_button)).perform(click());
        onView(withId(android.support.design.R.id.search_src_text)).perform(typeText("ted 2"));

        // Not a good way of waiting for the list to be filtered, I ran out of time to learn Espresso tests deeper!
        Thread.sleep(1000);

        // Verifying that the first item contains the text Fantasy
        onView(withId(R.id.moviesRecyclerView))
                .check(matches(atPosition(0, hasDescendant(withText("Fantasy")))));

        Espresso.unregisterIdlingResources(viewVisibilityIdlingResource);
    }

    // Found a matcher that helped me identify items at a specific position on a RecyclerView
    private Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                return viewHolder != null
                        && itemMatcher.matches(viewHolder.itemView);
            }
        };
    }
}