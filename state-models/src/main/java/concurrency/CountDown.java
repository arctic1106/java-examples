package concurrency;

import concurrency.threads.NumberCanvas;

import javax.swing.*;
import java.awt.*;

public class CountDown extends JFrame implements Runnable {
	final static int N = 10;
	Thread counter;
	int i;
	NumberCanvas display;

	public CountDown() {
		// Set up the display
		display = new NumberCanvas("CountDown");
		display.setPreferredSize(new Dimension(150, 100));
		add(display);

		// Frame settings
		setTitle("CountDown");
		setSize(200, 150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		CountDown app = new CountDown();
		app.start();
	}

	public void start() {
		counter = new Thread(this);
		i = N;
		counter.start();
	}

	public void stop() {
		counter = null;
	}

	public void run() {
		while (true) {
			if (counter == null) return;
			if (i > 0) {
				tick();
				--i;
			}
			if (i == 0) {
				return;
			}
		}
	}

	private void tick() {
		display.setvalue(i);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt(); // Properly handle the interrupt
		}
	}
}