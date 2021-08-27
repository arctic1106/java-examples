package dev.arcticsoft.examples;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * ListenerExamples
 *
 * @list 9.4
 * @list 9.5
 * @list 9.6
 * @list 9.8
 * @smell Good
 * @author Brian Goetz and Tim Peierls
 *
 * <p>
 * Example of GUI action listener.
 */
public class ListenerExamples {

    private final static ExecutorService exec = Executors.newCachedThreadPool();

    private final JButton colorButton = new JButton("Change color");
    private final Random random = new Random();

    public void backgroundRandom() {
        colorButton.addActionListener((ActionEvent e) -> colorButton.setBackground(new Color(random.nextInt())));
    }

    private final JButton computeButton = new JButton("Big computation");

    // List 9.4 Start a long-running task without feedback.
    public void longRunningTask() {
        computeButton.addActionListener(
                // Gets the long-running task out of the event thread in a “fire and forget” manner
                (ActionEvent e) -> {
                    exec.execute(() -> {/* Do big computation */ });
                }
        );
    }

    private final JButton button = new JButton("Do");
    private final JLabel label = new JLabel("idle");

    // List 9.5 Start a long-running with feedback
    public void longRunningTaskWithFeedback() {
        // On completion the task must submit another task to run in the event thread to update the user interface.
        button.addActionListener((ActionEvent e) -> {
            // Before executing the task, disable the button and set the label as busy.
            button.setEnabled(false);
            label.setText("busy");
            exec.execute(() -> {
                try {
                    /* Do big computation */
                } finally {
                    // When the task is completed, queue another task to re-enable the button and set the label as idle.
                    GuiExecutor.instance().execute(() -> {
                        button.setEnabled(true);
                        label.setText("idle");
                    });
                }
            });
        });
    }

    private final JButton startButton = new JButton("Start");
    private final JButton cancelButton = new JButton("Cancel");
    private Future<?> runningTask = null; // thread-confined

    public void taskWithCancellation() {
        // A task that polls the thread's interrupted status and returns early on interruption.
        startButton.addActionListener((ActionEvent e) -> {
            if (runningTask != null) {
                runningTask = exec.submit(new Runnable() {

                    @Override
                    public void run() {
                        while (moreWork()) {
                            if (Thread.currentThread().isInterrupted()) {
                                cleanUpPartialWork();
                                break;
                            }
                            doSomeWork();
                        }
                    }

                    private boolean moreWork() {
                        return false;
                    }

                    private void cleanUpPartialWork() {
                    }

                    private void doSomeWork() {
                    }
                });
            }
        });

        cancelButton.addActionListener((ActionEvent event) -> {
            if (runningTask != null) {
                runningTask.cancel(true);
            }
        });
    }

    public void runInBackground(final Runnable task) {                       
        startButton.addActionListener((ActionEvent e) -> {
            class CancelListener implements ActionListener {

                BackgroundTask<?> task;

                @Override
                public void actionPerformed(ActionEvent event) {
                    if (task != null) {
                        task.cancel(true);
                    }
                }
            }
            final CancelListener listener = new CancelListener();
            listener.task = new BackgroundTask<Void>() {
                @Override
                public Void compute() {
                    while (moreWork() && !isCancelled()) {
                        doSomeWork();
                    }
                    return null;
                }

                private boolean moreWork() {
                    return false;
                }

                private void doSomeWork() {
                }

                public void onCompletion(boolean cancelled, String s, Throwable exception) {
                    cancelButton.removeActionListener(listener);
                    label.setText("done");
                }
            };
            cancelButton.addActionListener(listener);
            exec.execute(task);
        });
    }
}
