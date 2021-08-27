package concurrency.threads;

class Slave implements Runnable {
	int rotations = 0;

	public void run() {
		try {
			while (!ThreadPanel.rotate()) ++rotations;
		} catch (InterruptedException e) {
		}
	}

	int result() {
		return rotations;
	}
}
