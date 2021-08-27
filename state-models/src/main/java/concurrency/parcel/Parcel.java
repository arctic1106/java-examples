package concurrency.parcel;

import java.awt.*;

public class Parcel {
	public static final int SIZE = 20;
	public static final int DOWN = 0;
	public static final int LEFT = -1;
	public static final int RIGHT = 1;
	final static int INC = 2;

	public int x;
	public int y;
	public Color c;
	public boolean displayIt = true;
	int destination;
	ParcelCanvas display;

	public Parcel(int d, Color c, ParcelCanvas display) {
		this.c = c;
		this.destination = d;
		this.display = display;
		display.addParcel(this);
		Dimension dc = display.getSize();
		x = (dc.width - SIZE) / 2;
		y = 0;
	}

	void move(int dir) {
		x += dir * INC;
		y += INC;
	}

	public void remove() {
		display.removeParcel(this);
	}

	void hide(boolean b) {
		displayIt = b;
	}
}