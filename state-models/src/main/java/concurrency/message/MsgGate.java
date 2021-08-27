package concurrency.message;

import concurrency.threads.ThreadPanel;

public class MsgGate implements Runnable {
	private final Channel<Signal> chan;
	private final Signal signal = new Signal();

	public MsgGate(Channel<Signal> c) {
		chan = c;
	}

	public void run() {
		try {
			while (true) {
				ThreadPanel.rotate(12);
				chan.send(signal);
				ThreadPanel.rotate(348);
			}
		} catch (InterruptedException e) {
		}
	}
}