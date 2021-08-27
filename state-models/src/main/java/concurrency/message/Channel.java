package concurrency.message;

public class Channel<T> extends Selectable {
	T chan_ = null;

	public synchronized void send(T v) throws InterruptedException {
		chan_ = v;
		signal();
		while (chan_ != null) wait();
	}

	public synchronized T receive() throws InterruptedException {
		block();
		clearReady();
		T tmp = chan_;
		chan_ = null;
		notifyAll(); //should be notify()
		return (tmp);
	}
}