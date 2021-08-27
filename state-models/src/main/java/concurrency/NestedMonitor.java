package concurrency;

import concurrency.buffers.Buffer;
import concurrency.buffers.Consumer;
import concurrency.buffers.DisplaySemaBuffer;
import concurrency.buffers.Producer;

public class NestedMonitor extends BoundedBuffer {

	public void start() {
		Buffer<Character> b = new DisplaySemaBuffer(buffDisplay, 5);
		prod.start(new Producer(b));
		cons.start(new Consumer(b));
	}
}