package dev.arcticsoft.examples;

import java.util.concurrent.CountDownLatch;

/**
 * Using CountDownLatch for starting and stopping threads in timing tests
 */
public class TestHarness {

    /**
     * TestHarness creates a number of threads that run a given task
     * concurrently. It uses two latches, a "starting gate" and an "ending
     * gate".
     *
     * The starting gate is initialized with a count of one; the ending gate is
     * initialized with a count equal to to the number of worker threads.
     */
    public long timeTasks(final int nThreads, final Runnable task) throws InterruptedException {
        var startGate = new CountDownLatch(1);
        var endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            final Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        /**
                         * The first thing each worker does is wait on the
                         * starting gate; this ensures that none of them start
                         * working until they all are ready to start.
                         */
                        System.out.printf("Thread:%s waiting for the starting gate.\n", Thread.currentThread().threadId());
                        startGate.await();
                        try {
                            task.run();
                        } finally {
                            /**
                             * The last thing each worker does is count down on
                             * the ending gate; this allows the master thread to
                             * wait efficiently until the last of the worker
                             * threads has finished; so it can calculate the
                             * elapsed time.
                             */
                            System.out.printf("Thread:%s arrived at the ending gate with countdown=%s\n", Thread.currentThread().threadId(), endGate.getCount());
                            endGate.countDown();
                        }
                    } catch (final InterruptedException ignored) {
                    }
                }
            };
            t.start();
        }

        final long start = System.nanoTime();
        System.out.println("Releasing the starting gate...");
        startGate.countDown();
        endGate.await();
        System.out.printf("The count of the ending gate has reached %s, all threads have finished.", endGate.getCount());
        final long end = System.nanoTime();
        return end - start;
    }

    public static void main(final String[] args) throws InterruptedException {
        var testHarness = new TestHarness();
        testHarness.timeTasks(
                4,
                () -> System.out.printf("Thread:%s is doing something important.\n", Thread.currentThread().threadId())
        );
    }
}
