package dev.arcticsoft.examples;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Web server that starts a new thread for each request
 *
 * A more responsive approach is to create a new thread for servicing each
 * request.
 *
 * The problem with the thread-per-task approach is that nothing places any
 * limit on the number of threads created except the rate at which remote users
 * can throw HTTP requests at it.
 *
 * Creating too many threads can cause your entire application to crash
 * horribly.
 */
public class ThreadPerTaskWebServer {

    public static void main(final String[] args) throws IOException {
        try (ServerSocket socket = new ServerSocket(80)) {
            /**
             * Similar to the single-threaded version - the main thread still
             * alternates between accepting an incoming connection and
             * dispatching the request.
             *
             * The difference is that for each connection, the main loop creates
             * a new thread to process the request instead of processing it with
             * the main thread.
             */
            while (true) {
                var connection = socket.accept();
                final Runnable task = () -> {
                    /**
                     * Consequence: Task-handling code must be thread-safe,
                     * because it may be invoked concurrently for multiple
                     * tasks.
                     */
                    handleRequest(connection);
                };
                new Thread(task).start();
            }
        }
    }

    private static void handleRequest(final Socket connection) {
        // request-handling logic here
    }
}
