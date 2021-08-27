package dev.arcticsoft.examples;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class TimingThreadPoolDemo {

    public static void main(final String[] args) throws InterruptedException {
        try (ExecutorService exec = new TimingThreadPool()) {
            System.out.printf("Starting {} instance.", exec.getClass().getSimpleName());
            IntStream.rangeClosed(0, 1).forEach((_) -> {
                exec.execute(() -> System.out.printf("Running in thread:{}", Thread.currentThread().threadId()));
            });
            System.out.println("Shutting down executor service.");
            exec.shutdown();
            exec.awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
