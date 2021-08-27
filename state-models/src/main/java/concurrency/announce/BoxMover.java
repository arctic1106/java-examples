package concurrency.announce;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoxMover extends Thread {
	private final BoxCanvas display;
	private final int id;
	private final int delay;
	private final int MaxX;
	private final int MaxY;
	private final MyListener listener = new MyListener();
	private int x;
	private int y;
	private boolean hit = false;

	public BoxMover(BoxCanvas d, int id, int delay) {
		display = d;
		display.addMouseListener(listener); // register action
		this.id = id;
		this.delay = delay;
		MaxX = d.getSize().width;
		MaxY = d.getSize().height;
	}

	synchronized void isHit(int mx, int my) {    //should be private but netscape 4.06
		hit = (mx > x && mx < x + BoxCanvas.BOXSIZE        //gives a security violation if it is.
				&& my > y && my < y + BoxCanvas.BOXSIZE);
		if (hit) notify();
	}

	private synchronized boolean waitHit()
			throws InterruptedException {
		wait(delay);
		return hit;
	}

	public void run() {
		try {
			while (true) {
				if (waitHit()) break;
				x = (int) (MaxX * Math.random());
				y = (int) (MaxY * Math.random());
				display.moveBox(id, x, y);
			}
		} catch (InterruptedException e) {
		}
		display.blackBox(id, x, y);
		display.removeMouseListener(listener); // deregister
	}


	class MyListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			isHit(e.getX(), e.getY());
		}
	}
}