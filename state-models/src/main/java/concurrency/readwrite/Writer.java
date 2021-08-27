package concurrency.readwrite;

import concurrency.threads.ThreadPanel;

class Writer implements Runnable {
	ReadWrite lock_;

	Writer(ReadWrite lock) {
		lock_ = lock;
	}

	public void run() {
		try {
			while (true) {
				while (!ThreadPanel.rotate()) ;
				// begin critical section
				lock_.acquireWrite();
				while (ThreadPanel.rotate()) ;
				lock_.releaseWrite();
			}
		} catch (InterruptedException e) {
		}
	}
}
