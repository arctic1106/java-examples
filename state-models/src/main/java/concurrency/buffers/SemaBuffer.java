package concurrency.buffers;

import concurrency.semaphore.Semaphore;

class SemaBuffer<E> implements Buffer<E> {
	protected E[] buffer;
	protected int in = 0;
	protected int out = 0;
	protected int count = 0;
	protected int size;

	Semaphore full; //counts number of items
	Semaphore empty;//counts number of spaces

	SemaBuffer(int size) {
		this.size = size;
		buffer = (E[]) new Object[size];
		full = new Semaphore(0);
		empty = new Semaphore(size);
	}

	synchronized public void put(E o)
			throws InterruptedException {
		empty.down();
		buffer[in] = o;
		++count;
		in = (in + 1) % size;
		full.up();
	}

	synchronized public E get()
			throws InterruptedException {
		full.down();
		E o = buffer[out];
		buffer[out] = null;
		--count;
		out = (out + 1) % size;
		empty.up();
		return (o);
	}
}