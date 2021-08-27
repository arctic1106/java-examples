package concurrency.diners;

public class Fork {
	private final PhilCanvas display;
	private final int identity;
	private boolean taken = false;

	public Fork(PhilCanvas disp, int id) {
		display = disp;
		identity = id;
	}

	synchronized void put() {
		taken = false;
		display.setFork(identity, taken);
		notify();
	}

	synchronized void get()
			throws java.lang.InterruptedException {
		while (taken) wait();
		taken = true;
		display.setFork(identity, taken);
	}
}
