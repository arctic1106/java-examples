package concurrency.threads;


public class Master implements Runnable {
	ThreadPanel threadPanel;
	SlotPanel slotPanel;

	public Master(ThreadPanel threadPanel, SlotPanel slotPanel) {
		this.threadPanel = threadPanel;
		this.slotPanel = slotPanel;
	}

	public void run() {
		try {
			String res = null;
			while (true) {
				while (!ThreadPanel.rotate()) ;
				if (res != null) slotPanel.leave(res);
				// create new slave thread
				Slave s = new Slave();
				Thread st = threadPanel.start(s, false);
				// continue execution
				while (ThreadPanel.rotate()) ;
				// wait for slave termination
				st.join();
				// get and display result from slave
				res = String.valueOf(s.result());
				slotPanel.enter(res);
			}
		} catch (InterruptedException e) {
		}
	}
}
