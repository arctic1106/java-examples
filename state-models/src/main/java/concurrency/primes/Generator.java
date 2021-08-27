package concurrency.primes;

import concurrency.Primes;

public class Generator extends Thread {
	static int MAX = 50;
	private final PrimesCanvas display;
	private final Pipe<Integer> out;

	public Generator(Pipe<Integer> c, PrimesCanvas d) {
		out = c;
		display = d;
	}

	public void run() {
		try {
			for (int i = 2; i <= MAX; ++i) {
				display.print(0, i);
				out.put(i);
				sleep(500);
			}
			display.print(0, Primes.EOS);
			out.put(Primes.EOS);
		} catch (InterruptedException e) {
		}
	}
}