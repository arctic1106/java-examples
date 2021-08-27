package concurrency.diners;

import concurrency.Diners;

public class Philosopher extends Thread {
	private final int identity;
	private final PhilCanvas view;
	private final Diners controller;
	private final Fork left;
	private final Fork right;

	public Philosopher(Diners ctr, int id, Fork l, Fork r) {
		controller = ctr;
		view = ctr.display;
		identity = id;
		left = l;
		right = r;
	}

	public void run() {
		try {
			while (true) {
				//thinking
				view.setPhil(identity, PhilCanvas.THINKING);
				sleep(controller.sleepTime());
				//hungry
				view.setPhil(identity, PhilCanvas.HUNGRY);
				right.get();
				//gotright chopstick
				view.setPhil(identity, PhilCanvas.GOTRIGHT);
				sleep(500);
				left.get();
				//eating
				view.setPhil(identity, PhilCanvas.EATING);
				sleep(controller.eatTime());
				right.put();
				left.put();
			}
		} catch (java.lang.InterruptedException e) {
		}
	}
}