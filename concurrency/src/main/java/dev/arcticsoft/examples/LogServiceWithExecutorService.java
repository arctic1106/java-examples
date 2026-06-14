package dev.arcticsoft.examples;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.ExecutorService;
import static java.util.concurrent.Executors.newSingleThreadExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Logging service that uses an ExecutorService
 *
 * LogServiceWithExecutorService delegates to an ExecutorService instead of
 * managing its own thread.
 *
 * Encapsulating an ExecutorService extends the ownership chain from application
 * to service to thread by adding another link; each member of the chain manages
 * the lifecycle of the services or threads it owns.
 */
public class LogServiceWithExecutorService {

    private final ExecutorService exec = newSingleThreadExecutor();
    private final PrintWriter writer;

    public LogServiceWithExecutorService(final Writer writer) {
        this.writer = new PrintWriter(writer);
    }

    public void start() {

    }

    public void stop() throws InterruptedException {
        try (this.writer) {
            this.exec.shutdown();
            this.exec.awaitTermination(10, TimeUnit.SECONDS);
        }
    }

    public void log(final String msg) {
        this.exec.execute(new WriteTask(msg));
    }

    private class WriteTask implements Runnable {

        private final String msg;

        public WriteTask(final String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            LogServiceWithExecutorService.this.writer.println(this.msg);
        }
    }
}
