package concurrency.buffers;

public class DisplaySemaBuffer extends SemaBuffer<Character> {
	private final BufferCanvas disp;
	private final char[] tmp;

	public DisplaySemaBuffer(BufferCanvas disp, int size) {
		super(size);
		this.disp = disp;
		tmp = new char[size];
	}

	synchronized public void put(Character c) throws InterruptedException {
		int oldin = in;
		super.put(c);
		tmp[oldin] = c;
		disp.setvalue(tmp, in, out);
		Thread.sleep(400);
	}

	synchronized public Character get() throws InterruptedException {
		int oldout = out;
		Character c = super.get();
		tmp[oldout] = ' ';
		disp.setvalue(tmp, in, out);
		return (c);
	}
}