package concurrency.parcel;

import concurrency.time.TimeStop;
import concurrency.time.Timed;

public class Chute implements ParcelMover, Timed {
	public ParcelMover next = null;
	protected int i, T, direction;
	protected Parcel current = null;

	public Chute(int len, int dir) {
		T = len;
		direction = dir;
	}

	// package enters chute
	public void enter(Parcel p) {
		if (current != null) throw new RuntimeException();
		current = p;
		i = 0;
	}

	public void pretick() throws TimeStop {
		if (current == null) return;
		if (i == T) {
			next.enter(current); // package leaves chute
			current = null;
		}
	}

	public void tick() {
		if (current == null) return;
		++i;
		current.move(direction);
	}
}