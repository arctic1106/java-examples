package concurrency.message;

import concurrency.threads.ThreadPanel;

public class Receiver implements Runnable {
	private final Channel<Integer> chan;
	private final SlotCanvas display;

	public Receiver(Channel<Integer> c, SlotCanvas d) {
		chan = c;
		display = d;
	}

	public void run() {
		try {
			Integer v = null;
			while (true) {
				ThreadPanel.rotate(180);
				if (v != null) display.leave(v.toString());
				v = chan.receive();
				display.enter(v.toString());
				ThreadPanel.rotate(180);
			}
		} catch (InterruptedException e) {
		}
	}
}
