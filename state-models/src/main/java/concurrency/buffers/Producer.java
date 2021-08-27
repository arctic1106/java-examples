package concurrency.buffers;

import concurrency.threads.ThreadPanel;

public class Producer implements Runnable {
	Buffer<Character> buffer;
	String alphabet = "abcdefghijklmnopqrstuvwxyz";

	public Producer(Buffer<Character> b) {
		buffer = b;
	}

	public void run() {
		try {
			int ai = 0;
			while (true) {
				ThreadPanel.rotate(12);
				buffer.put(alphabet.charAt(ai));
				ai = (ai + 1) % alphabet.length();
				ThreadPanel.rotate(348);
			}
		} catch (InterruptedException e) {
		}
	}
}
