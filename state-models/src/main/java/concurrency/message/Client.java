package concurrency.message;

import concurrency.threads.ThreadPanel;

public class Client implements Runnable {
	private Entry<String, String> entry;
	private SlotCanvas display;
	private String id;

	public Client(Entry<String, String> e, SlotCanvas d, String s) {
		entry = e;
		display = d;
		id = s;
	}

	public void run() {
		try {
			while (true) {
				ThreadPanel.rotate(90);
				display.enter(id);
				String result = entry.call(id);
				display.leave(id);
				display.enter(result);
				ThreadPanel.rotate(90);
				display.leave(result);
				ThreadPanel.rotate(180);
			}
		} catch (InterruptedException e) {
		}
	}
}
