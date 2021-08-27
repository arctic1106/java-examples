package concurrency.buffers;

import concurrency.threads.ThreadPanel;

public class Consumer implements Runnable {
	Buffer<Character> buffer;

	public Consumer(Buffer<Character> b) {
		buffer = b;
	}

	public void run() {
		try {
			while (true) {
				ThreadPanel.rotate(180);
				Character c = buffer.get();
				ThreadPanel.rotate(180);
			}
		} catch (InterruptedException _) {
		}
	}
}
