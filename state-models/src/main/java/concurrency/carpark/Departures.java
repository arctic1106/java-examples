package concurrency.carpark;

import concurrency.threads.ThreadPanel;

public class Departures implements Runnable {

	CarParkControl carpark;

	public Departures(CarParkControl c) {
		carpark = c;
	}

	public void run() {
		try {
			while (true) {
				ThreadPanel.rotate(180);
				carpark.depart();
				ThreadPanel.rotate(180);

			}
		} catch (InterruptedException e) {
		}
	}
}
