package dev.arcticsoft.examples;

import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * GuiExecutor
 *
 * @list 9.2
 * @smell Good
 * @author Brian Goetz and Tim Peierls
 *
 * <p>
 * An Executor that delegates tasks to {@code SwingUtilities} for execution
 */
public class GuiExecutor extends AbstractExecutorService {

    private static final GuiExecutor instance = new GuiExecutor();   // Singletons have a private constructor and a public factory

    private GuiExecutor() {
    }

    public static GuiExecutor instance() {
        return instance;
    }

    @Override
    public void execute(Runnable r) {
        if (SwingUtilities.isEventDispatchThread()) {
            r.run();
        } else {
            SwingUtilities.invokeLater(r);
        }
    }

    @Override
    public void shutdown() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Runnable> shutdownNow() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit)
            throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isShutdown() {
        return false;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }
}
