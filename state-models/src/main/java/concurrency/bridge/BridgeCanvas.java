package concurrency.bridge;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BridgeCanvas extends JPanel {
	final static int initredX = 5;
	final static int initredY = 55;
	final static int initblueX = 410;
	final static int initblueY = 130;
	final static int bridgeY = 90;
	private final int cycleTime = 20;
	private BufferedImage redCar;
	private BufferedImage blueCar;
	private BufferedImage bridge;
	private int[] redX, redY, blueX, blueY;
	private int maxCar = 2;
	private boolean frozen = false;

	public BridgeCanvas() {
		setPreferredSize(new Dimension(bridge.getWidth(), bridge.getHeight()));
		loadImages();
		init(1);
	}

	private void loadImages() {
		try {
			redCar = ImageIO.read(getClass().getResource("/image/redcar.gif"));
			blueCar = ImageIO.read(getClass().getResource("/image/bluecar.gif"));
			bridge = ImageIO.read(getClass().getResource("/image/bridge.gif"));
		} catch (IOException e) {
			System.out.println("Couldn't load one of the images");
		}
	}

	public void init(int ncars) {
		maxCar = ncars;
		frozen = false;
		redX = new int[maxCar];
		redY = new int[maxCar];
		blueX = new int[maxCar];
		blueY = new int[maxCar];

		for (int i = 0; i < maxCar; i++) {
			redX[i] = initredX - i * 85;
			redY[i] = initredY;
			blueX[i] = initblueX + i * 85;
			blueY[i] = initblueY;
		}
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); // Ensure proper rendering
		Dimension d = getSize();
		g.drawImage(bridge, 0, 0, this);

		for (int i = 0; i < maxCar; i++) {
			g.drawImage(redCar, redX[i], redY[i], this);
			g.drawImage(blueCar, blueX[i], blueY[i], this);
		}
		if (blueY[0] == redY[0] && Math.abs(redX[0] + 80 - blueX[0]) < 5) {
			g.setColor(Color.red);
			g.drawString("Crunch!", 200, 100);
			frozen = true;
		}
	}

	public boolean moveRed(int i) throws InterruptedException {
		int X = redX[i];
		int Y = redY[i];
		synchronized (this) {
			while (frozen)
				wait();
			if (i == 0 || Math.abs(redX[i - 1] - X) > 120) {
				X += 2;
				if (X >= 500) {
					X = -80;
					Y = initredY;
				}
				if (X >= 60 && X < 290 && Y < bridgeY)
					++Y;
				if (X >= 290 && Y > initredY)
					--Y;
			}
			redX[i] = X;
			redY[i] = Y;
			repaint();
		}
		Thread.sleep(cycleTime);
		return (X > 25 && X < 400);
	}

	public boolean moveBlue(int i) throws InterruptedException {
		int X = blueX[i];
		int Y = blueY[i];
		synchronized (this) {
			while (frozen)
				wait();
			if (i == 0 || Math.abs(blueX[i - 1] - X) > 120) {
				X -= 2;
				if (X <= -80) {
					X = 500;
					Y = initblueY;
				}
				if (X <= 370 && X > 130 && Y > bridgeY)
					--Y;
				if (X <= 130 && Y < initblueY)
					++Y;
				blueX[i] = X;
			}
			blueY[i] = Y;
			repaint();
		}
		Thread.sleep(cycleTime);
		repaint();
		return (X > 25 && X < 400);
	}

	public synchronized void freeze() {
		frozen = true;
	}

	public synchronized void thaw() {
		frozen = false;
		notifyAll();
	}
}