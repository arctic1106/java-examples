package concurrency.threads;

public class Rotator implements Runnable {

	public void run() {
		try {
			while (true) ThreadPanel.rotate();
		} catch (InterruptedException e) {
		}
	}
}