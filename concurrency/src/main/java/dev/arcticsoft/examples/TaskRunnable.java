package dev.arcticsoft.examples;

import java.util.concurrent.BlockingQueue;

/**
 * TaskRunnable
 *
 * @list 5.10
 * @smell Good
 * @author Brian Goetz and Tim Peierls
 *
 * <p>
 * The example of restoring the interrupted status so as not to swallow the
 * interrupt.
 */
public class TaskRunnable implements Runnable {

    BlockingQueue<Task> queue;

    @Override
    public void run() {
        try {
            processTask(queue.take());
        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();            // Restore interrupted status
        }
    }

    void processTask(Task task) {
        // Handle the task
    }

    interface Task {
    }
}
