package concurrency;

import concurrency.threads.Rotator;
import concurrency.threads.ThreadPanel;

import javax.swing.*;
import java.awt.*;

public class ThreadDemo extends JFrame {
	private final ThreadPanel threadA;
	private final ThreadPanel threadB;

	public ThreadDemo() {
		threadA = new ThreadPanel("Thread A", Color.blue);
		threadB = new ThreadPanel("Thread B", Color.blue);

		setLayout(new GridLayout(1, 2));
		add(threadA);
		add(threadB);

		getContentPane().setBackground(Color.lightGray);

		// Window settings
		setTitle("Thread Demo");
		setSize(500, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		ThreadDemo demo = new ThreadDemo();
		demo.start();
	}

	public void start() {
		threadA.start(new Rotator());
		threadB.start(new Rotator());
	}

	public void stop() {
		threadA.stop();
		threadB.stop();
	}
}