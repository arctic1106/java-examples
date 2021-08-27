package concurrency.message;

import concurrency.threads.ThreadPanel;

public class Asender implements Runnable {
	private final Port<Integer> port;
	private final SlotCanvas display;

	public Asender(Port<Integer> p, SlotCanvas d) {
		port = p;
		display = d;
	}

	public void run() {
		try {
			int ei = 0;
			while (true) {
				display.enter(String.valueOf(ei));
				ThreadPanel.rotate(90);
				port.send(ei);
				display.leave(String.valueOf(ei));
				ei = (ei + 1) % 10;
				ThreadPanel.rotate(270);
			}
		} catch (InterruptedException e) {
		}
	}
}
