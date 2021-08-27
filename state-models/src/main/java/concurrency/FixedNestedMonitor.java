package concurrency;

import concurrency.buffers.Buffer;
import concurrency.buffers.Consumer;
import concurrency.buffers.DisplayFixedSemaBuffer;
import concurrency.buffers.Producer;

public class FixedNestedMonitor extends BoundedBuffer {

	public void start() {
		Buffer<Character> b = new DisplayFixedSemaBuffer(buffDisplay, 5);
		prod.start(new Producer(b));
		cons.start(new Consumer(b));
	}
}