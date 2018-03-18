package movielister.andreas.com.movielister.core;

import io.reactivex.Scheduler;

/**
 * Created by andreas on 27/01/18.
 */

public interface SchedulersProvider {

    Scheduler io();

    Scheduler computation();

    Scheduler mainThread();

}
