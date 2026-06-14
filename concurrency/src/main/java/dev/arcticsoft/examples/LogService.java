package dev.arcticsoft.examples;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Adding reliable cancellation to LogWriter
 */
public class LogService {

    private final BlockingQueue<String> queue;
    private final LoggerThread loggerThread;
    private final PrintWriter writer;
    private boolean isShutdown;
    private int reservations;

    public LogService(final Writer writer) {
        this.queue = new LinkedBlockingQueue<>();
        this.loggerThread = new LoggerThread();
        this.writer = new PrintWriter(writer);
    }

    public void start() {
        this.loggerThread.start();
    }

    public void stop() {
        synchronized (this) {
            this.isShutdown = true;
        }
        this.loggerThread.interrupt();
    }

    public void log(final String msg) throws InterruptedException {
        /**
         * We do not want to hold a lock while trying to enqueue the message,
         * since the BlockingQueue.put could block. Instead, we can atomically
         * check for shutdown and conditionally increment a counter to "reserve"
         * the right to submit a message.
         */
        synchronized (this) {
            if (this.isShutdown) {
                throw new IllegalStateException(/*...*/);
            }
            ++this.reservations;
        }
        this.queue.put(msg);
    }

    private class LoggerThread extends Thread {

        @Override
        public void run() {
            try (LogService.this.writer) {
                while (true) {
                    try {
                        synchronized (LogService.this) {
                            if (LogService.this.isShutdown && LogService.this.reservations == 0) {
                                break;
                            }
                        }
                        final String msg = LogService.this.queue.take();
                        synchronized (LogService.this) {
                            --LogService.this.reservations;
                        }
                        LogService.this.writer.println(msg);
                    } catch (final InterruptedException e) {
                        /* retry */
                    }
                }
            }
        }
    }
}
