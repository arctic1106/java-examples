package concurrency.garden;

import concurrency.Garden;
import concurrency.threads.NumberCanvas;

public class Turnstile extends Thread {
	private final NumberCanvas display;
	private final Counter people;

	public Turnstile(NumberCanvas n, Counter c) {
		display = n;
		people = c;
	}

	public void run() {
		try {
			display.setvalue(0);
			for (int i = 1; i <= Garden.MAX; i++) {
				Thread.sleep(500); //0.5 second
				display.setvalue(i);
				people.increment();
			}
		} catch (InterruptedException e) {
		}
	}

	public boolean isRunning() {
		return people.value < Garden.MAX;
	}
}
