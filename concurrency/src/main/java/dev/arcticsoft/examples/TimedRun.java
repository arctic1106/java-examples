package dev.arcticsoft.examples;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * TimedRun
 *
 * @list 7.10
 * @smell Good
 * @author Brian Goetz and Tim Peierls
 *
 * <p>
 * The example of an attempt at running an arbitrary Runnable for a given amount
 * of time by Future.
 */
public class TimedRun {

    private static final ExecutorService taskExec = Executors.newCachedThreadPool();

    public static void timedRun(Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        var task = taskExec.submit(r);
        try {
            task.get(timeout, unit);
        } // If timeout is reached, Future.get() will throw a TimeoutException.
        catch (TimeoutException | ExecutionException e) {
            // task will be cancelled below
        } // If the underlying computation throws an exception prior to cancellation,
        // it is rethrown from timedRun, which is the most convenient way for the caller to deal with the exception.
        finally {
            // Cancel tasks whose result is no longer needed.
            task.cancel(true);
        }
    }
}
