package concurrency.message;

import concurrency.threads.ThreadPanel;

public class Sender implements Runnable {
	private final Channel<Integer> chan;
	private final SlotCanvas display;

	public Sender(Channel<Integer> c, SlotCanvas d) {
		chan = c;
		display = d;
	}

	public void run() {
		try {
			int ei = 0;
			while (true) {
				display.enter(String.valueOf(ei));
				ThreadPanel.rotate(12);
				chan.send(ei);
				display.leave(String.valueOf(ei));
				ei = (ei + 1) % 10;
				ThreadPanel.rotate(348);
			}
		} catch (InterruptedException e) {
		}
	}
}