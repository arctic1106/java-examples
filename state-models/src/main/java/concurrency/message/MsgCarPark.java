package concurrency.message;

import concurrency.threads.ThreadPanel;

public class MsgCarPark implements Runnable {
	private final Channel<Signal> arrive;
	private final Channel<Signal> depart;
	private final int N;
	private final StringCanvas disp;
	private int spaces;

	public MsgCarPark(Channel<Signal> a, Channel<Signal> l, StringCanvas d, int capacity) {
		depart = l;
		arrive = a;
		N = spaces = capacity;
		disp = d;
		disp.setString("Cars: " + 0 + "   Spaces: " + spaces);
	}

	private void display(int s) throws InterruptedException {
		disp.setString("Cars: " + (N - s) + "   Spaces: " + s);
		ThreadPanel.rotate(348);
	}

	public void run() {
		try {
			var sel = new Select();
			sel.add(depart);
			sel.add(arrive);
			while (true) {
				ThreadPanel.rotate(12);
				arrive.guard(spaces > 0);
				depart.guard(spaces < N);
				switch (sel.choose()) {
					case 1:
						depart.receive();
						display(++spaces);
						break;
					case 2:
						arrive.receive();
						display(--spaces);
						break;
				}
			}
		} catch (InterruptedException e) {
		}
	}
}