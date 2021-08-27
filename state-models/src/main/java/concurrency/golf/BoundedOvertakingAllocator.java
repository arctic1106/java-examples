package concurrency.golf;

import java.util.BitSet;

public class BoundedOvertakingAllocator implements Allocator {
	private final int TM; //must be maximum active threads + bound
	private final int bound;
	private final BitSet overtakers;
	private int available;
	private int turn = 1;
	private int next = 1;
	private int overtaken = 0;

	public BoundedOvertakingAllocator(int n, int b) {
		available = n;
		bound = b;
		TM = 10000 + b;
		overtakers = new BitSet(TM + 1);
	}

	synchronized public void get(int n)
			throws InterruptedException {
		int myturn = turn;
		turn = turn % TM + 1;
		while (n > available || (myturn != next && overtaken >= bound)) {
			wait();
		}
		if (myturn != next) {
			overtakers.set(myturn);
			++overtaken;
		} else {
			next = next % TM + 1;
			while (overtakers.get(next)) {
				overtakers.clear(next);
				--overtaken;
				next = next % TM + 1;
			}
		}
		available -= n;
		notifyAll();
	}

	synchronized public void put(int n) {
		available += n;
		notifyAll();
	}
}