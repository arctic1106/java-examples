package concurrency.carpark;

/*********************CARPARK CONTROL*****************************/

public class CarParkControl {

	protected int spaces;
	protected int capacity;

	CarParkControl(int n) {
		capacity = spaces = n;
	}

	synchronized void arrive() throws InterruptedException {
		while (spaces == 0) wait();
		--spaces;
		notifyAll();
	}

	synchronized void depart() throws InterruptedException {
		while (spaces == capacity) wait();
		++spaces;
		notifyAll();
	}
}
