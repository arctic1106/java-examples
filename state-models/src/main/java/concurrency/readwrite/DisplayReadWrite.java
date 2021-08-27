package concurrency.readwrite;

import concurrency.message.StringCanvas;

class DisplayReadWrite implements ReadWrite {
	StringCanvas display;
	ReadWrite lock;
	private int readers = 0;
	private boolean writing = false;

	DisplayReadWrite(StringCanvas t, ReadWrite lock) {
		display = t;
		this.lock = lock;
		setdisplay();
	}

	private void setdisplay() {
		display.setString("readers= " + readers + "  writing= " + writing);
	}

	public void acquireRead() throws InterruptedException {
		lock.acquireRead();
		++readers;
		setdisplay();
	}

	public void releaseRead() {
		lock.releaseRead();
		--readers;
		setdisplay();
	}

	public void acquireWrite() throws InterruptedException {
		lock.acquireWrite();
		writing = true;
		setdisplay();
	}

	public void releaseWrite() {
		lock.releaseWrite();
		writing = false;
		setdisplay();
	}
}