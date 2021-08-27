package concurrency.parcel;

import concurrency.time.Timed;

public class DestinationBin implements ParcelMover, Timed {
	static int FLASHPERIOD = 10;
	private final int destination;
	private Parcel current;
	private boolean flash = false;
	private boolean off = false;
	private int tickCount = 0;

	public DestinationBin(int destination) {
		this.destination = destination;
	}

	public void enter(Parcel p) {
		if (current != null) current.remove();
		current = p;
		flash = !(p.destination == destination);
	}

	public void pretick() {
	}

	public void tick() {
		tickCount = (tickCount + 1) % FLASHPERIOD;
		if (tickCount == 0 && flash) {
			off = !off;
			current.hide(off);
		}
	}
}