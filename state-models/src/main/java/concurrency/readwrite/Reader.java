package concurrency.readwrite;

import concurrency.threads.ThreadPanel;

class Reader implements Runnable {

	ReadWrite lock_;

	Reader(ReadWrite lock) {
		lock_ = lock;
	}

	public void run() {
		try {
			while (true) {
				while (!ThreadPanel.rotate()) ;
				// begin critical section
				lock_.acquireRead();
				while (ThreadPanel.rotate()) ;
				lock_.releaseRead();
			}
		} catch (InterruptedException e) {
		}
	}
}
