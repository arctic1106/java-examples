package concurrency.garden;

import concurrency.threads.NumberCanvas;

public class SynchronizedCounter extends Counter {

	public SynchronizedCounter(NumberCanvas n) {
		super(n);
	}

	public synchronized void increment() {
		super.increment();
	}
}