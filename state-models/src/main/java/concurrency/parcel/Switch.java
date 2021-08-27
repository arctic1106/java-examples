package concurrency.parcel;

public class Switch extends Chute {
	private final ParcelCanvas display;
	private final int gate;
	public ParcelMover left = null;
	public ParcelMover right = null;

	public Switch(int len, int dir, int g, ParcelCanvas d) {
		super(len, dir);
		display = d;
		gate = g;
	}

	public void setSwitch(int direction) {
		if (current == null) {  // nothing passing through switch
			display.setGate(gate, direction);
			if (direction == 0)
				next = left;
			else
				next = right;
		}
	}
}