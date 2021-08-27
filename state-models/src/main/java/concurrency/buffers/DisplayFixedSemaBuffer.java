package concurrency.buffers;

public class DisplayFixedSemaBuffer extends FixedSemaBuffer<Character> {
	private final BufferCanvas disp;
	private final char[] tmp;

	public DisplayFixedSemaBuffer(BufferCanvas disp, int size) {
		super(size);
		this.disp = disp;
		tmp = new char[size];
	}

	public void put(Character c) throws InterruptedException {
		int oldin = in;
		super.put(c);
		synchronized (this) {
			tmp[oldin] = c;
			disp.setvalue(tmp, in, out);
			Thread.sleep(400);
		}
	}

	public Character get() throws InterruptedException {
		int oldout = out;
		Character c = super.get();
		synchronized (this) {
			tmp[oldout] = ' ';
			disp.setvalue(tmp, in, out);
		}
		return (c);
	}
}