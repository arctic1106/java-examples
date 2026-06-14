package dev.arcticsoft.examples;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExecutorClientScheduler implements ClientScheduler {

    private final Executor executor;

    public ExecutorClientScheduler(int availableThreads) {
        this.executor = Executors.newFixedThreadPool(availableThreads);
    }

    @Override
    public void schedule(final ClientRequestProcessor requestProcessor) {
        this.executor.execute(requestProcessor::process);
    }
}
