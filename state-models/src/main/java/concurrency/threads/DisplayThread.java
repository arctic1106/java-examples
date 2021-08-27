package concurrency.threads;

import java.awt.*;

public class DisplayThread extends Thread {
	private static final int STEP = 6;
	private final GraphicPanel graphicPanel;
	private final int rate;
	private final Runnable target;
	private volatile boolean suspended;
	private int angle = 0;
	private int segStart = 9999;
	private int segEnd = 9999;
	private Color segColor = Color.CYAN;

	public DisplayThread(GraphicPanel graphicPanel, Runnable target, int rate, int split, boolean suspended) {
		this.graphicPanel = graphicPanel;
		this.target = target;
		this.rate = rate;
		setSplit(split);
		this.suspended = suspended;
		graphicPanel.setColor(suspended ? Color.RED : Color.GREEN);
	}

	public static boolean rotate() {
		var currentThread = (DisplayThread) Thread.currentThread();
		synchronized (currentThread) {
			currentThread.mysuspend();
			currentThread.angle = (currentThread.angle + STEP) % 360;
			currentThread.graphicPanel.setAngle(currentThread.angle);
			try {
				Thread.sleep(currentThread.rate);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			return currentThread.angle >= currentThread.segStart && currentThread.angle <= currentThread.segEnd;
		}
	}

	public static void setSegmentColor(Color color) {
		var currentThread = (DisplayThread) Thread.currentThread();
		synchronized (currentThread) {
			currentThread.segColor = color;
			currentThread.graphicPanel.setSegment(currentThread.segStart, currentThread.segEnd, currentThread.segColor);
		}
	}

	private synchronized void mysuspend() {
		try {
			while (suspended) wait();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}


	public void passivate() {
		if (!suspended) {
			suspended = true;
			graphicPanel.setColor(Color.RED);
		}
	}

	public void activate() {
		if (suspended) {
			suspended = false;
			graphicPanel.setColor(Color.GREEN);
			synchronized (this) {
				notify();
			}
		}
	}

	public synchronized void setSplit(int split) {
		segStart = (60 - split) * 3;
		segEnd = segStart + split * 6;
		graphicPanel.setSegment(segStart, segEnd, segColor);
	}

	@Override
	public void run() {
		mysuspend();
		target.run();
		graphicPanel.setColor(Color.RED);
		graphicPanel.setAngle(0);
	}
}