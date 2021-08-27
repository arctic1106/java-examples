package concurrency.garden;

import concurrency.threads.NumberCanvas;

public class Counter {
	int value = 0;
	NumberCanvas display;

	public Counter(NumberCanvas n) {
		display = n;
		display.setvalue(value);
	}

	public void increment() {
		int temp = value;   //read[v]
		Simulate.HWinterrupt();
		value = temp + 1;       //write[v+1]
		display.setvalue(value);
	}
}
