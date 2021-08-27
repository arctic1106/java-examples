package concurrency.message;

import concurrency.threads.ThreadPanel;

public class Areceiver implements Runnable {
	private final Port<Integer> port;
	private final SlotCanvas display;

	public Areceiver(Port<Integer> p, SlotCanvas d) {
		port = p;
		display = d;
	}

	public void run() {
		try {
			Integer v = null;
			while (true) {
				ThreadPanel.rotate(45);
				if (v != null) display.leave(v.toString());
				ThreadPanel.rotate(45);
				v = port.receive();
				display.enter(v.toString());
			}
		} catch (InterruptedException e) {
		}
	}
}
