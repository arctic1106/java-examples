package concurrency.carpark;

import concurrency.threads.ThreadPanel;

public class Arrivals implements Runnable {
	private final CarParkControl carpark;

	public Arrivals(CarParkControl c) {
		carpark = c;
	}

	public void run() {
		try {
			while (true) {
				ThreadPanel.rotate(330);
				carpark.arrive();
				ThreadPanel.rotate(30);
			}
		} catch (InterruptedException e) {
		}
	}
}