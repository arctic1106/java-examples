package concurrency;

import concurrency.semaphore.DisplaySemaphore;
import concurrency.semaphore.MutexLoop;
import concurrency.semaphore.Semaphore;
import concurrency.threads.NumberCanvas;
import concurrency.threads.ThreadPanel;

import javax.swing.*;
import java.awt.*;

public class SemaDemo extends JFrame {
	private ThreadPanel thread1;
	private ThreadPanel thread2;
	private ThreadPanel thread3;
	private NumberCanvas semaDisplay;

	public SemaDemo() {
		initUI();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SemaDemo frame = new SemaDemo();
			frame.setVisible(true);
			frame.start(); // Start threads after making the frame visible
		});
	}

	private void initUI() {
		setTitle("Semaphore Demo");
		setSize(600, 400); // Set the desired size for the window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // Center the window
		setLayout(new BorderLayout());

		semaDisplay = new NumberCanvas("Mutex");
		add(semaDisplay, BorderLayout.CENTER);

		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

		thread1 = new ThreadPanel("Thread 1", Color.blue, true);
		thread2 = new ThreadPanel("Thread 2", Color.blue, true);
		thread3 = new ThreadPanel("Thread 3", Color.blue, true);

		controlPanel.add(thread1);
		controlPanel.add(thread2);
		controlPanel.add(thread3);

		add(controlPanel, BorderLayout.SOUTH);

		setBackground(Color.lightGray);
	}

	public void start() {
		Semaphore mutex = new DisplaySemaphore(semaDisplay, 1);
		thread1.start(new MutexLoop(mutex));
		thread2.start(new MutexLoop(mutex));
		thread3.start(new MutexLoop(mutex));
	}

	public void stop() {
		thread1.stop();
		thread2.stop();
		thread3.stop();
	}
}