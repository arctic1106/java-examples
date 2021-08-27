package concurrency.parcel;

import concurrency.time.TimeStop;

public class SensorController implements ParcelMover {
	public ParcelMover next;
	public Switch aSwitch;
	protected int level;

	public SensorController(int level) {
		this.level = level;
	}

	// package enters and leaves within one clock cycle
	public void enter(Parcel p) throws TimeStop {
		int dir = (p.destination >> level) & 1;
		aSwitch.setSwitch(dir);
		next.enter(p);
	}
}