package concurrency.semaphore;

import concurrency.threads.ThreadPanel;

public class MutexLoop implements Runnable {
	Semaphore mutex;

	public MutexLoop(Semaphore sema) {
		mutex = sema;
	}

	public void run() {
		try {
			while (true) {
				while (!ThreadPanel.rotate()) ;
				// get mutual exclusion
				mutex.down();
				while (ThreadPanel.rotate()) ; //critical section
				//release mutual exclusion
				mutex.up();
			}
		} catch (InterruptedException e) {
		}
	}
}
