package concurrency.message;

import concurrency.threads.ThreadPanel;

import java.awt.*;

public class Server implements Runnable {
	private final Entry<String, String> entry;
	private final SlotCanvas display;

	public Server(Entry<String, String> e, SlotCanvas d) {
		entry = e;
		display = d;
	}

	public void run() {
		try {
			while (true) {
				while (!ThreadPanel.rotate()) ;
				String request = entry.accept();
				display.enter(request);
				if (request.equals("A"))
					ThreadPanel.setSegmentColor(Color.magenta);
				else
					ThreadPanel.setSegmentColor(Color.yellow);
				while (ThreadPanel.rotate()) ;
				display.leave(request);
				entry.reply("R");
			}
		} catch (InterruptedException e) {
		}
	}
}