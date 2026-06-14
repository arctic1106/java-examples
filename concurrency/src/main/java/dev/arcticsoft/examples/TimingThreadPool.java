package dev.arcticsoft.examples;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Thread pool extended with logging and timing
 *
 * TimingThreadPool shows a custom thread pool that uses beforeExecute,
 * afterExecute, and "terminated" to add logging and statistics gathering.
 */
public class TimingThreadPool extends ThreadPoolExecutor {

    public TimingThreadPool() {
        super(2, 2, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    /**
     * To measure a task's runtime, beforeExecute must record the start time and
     * store it somewhere afterExecute can find it. Because execution hooks are
     * called in the the thread that executes the task, a value placed in a
     * ThreadLocal by beforeExecute can be retrieved by afterExecute.
     */
    private final ThreadLocal<Long> startTime = new ThreadLocal<>();
    private final AtomicLong numTasks = new AtomicLong();
    private final AtomicLong totalTime = new AtomicLong();

    @Override
    protected void beforeExecute(final Thread t, final Runnable r) {
        super.beforeExecute(t, r);
        System.out.printf("Thread {}: start {}s", t, r.getClass().getSimpleName());
        // set the ThreadLocal value of currently executing pool thread
        this.startTime.set(System.nanoTime());
    }

    @Override
    protected void afterExecute(final Runnable r, final Throwable t) {
        try {
            var endTime = System.nanoTime();
            // read the ThreadLocal value set by currently executing pool thread in
            // "beforeExecute" above
            var taskTime = endTime - this.startTime.get();
            this.numTasks.incrementAndGet();
            this.totalTime.addAndGet(taskTime);
            System.out.printf("Thread {}: end {}, time={}}", Thread.currentThread().getName(), r.getClass().getSimpleName(), taskTime);
        } finally {
            super.afterExecute(r, t);
        }
    }

    @Override
    protected void terminated() {
        try {
            System.out.printf("Terminated: avg time={}ns", this.totalTime.get() / this.numTasks.get());
        } finally {
            super.terminated();
        }
    }
}
