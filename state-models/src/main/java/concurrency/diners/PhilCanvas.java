package concurrency.diners;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class PhilCanvas extends JPanel {
	public static final int NUMPHILS = 5;
	public static final int THINKING = 0;
	public static final int HUNGRY = 1;
	public static final int GOTRIGHT = 2;
	public static final int EATING = 3;
	public static final int GOTLEFT = 4;

	BufferedImage[] imgs = new BufferedImage[5];
	AffineTransform[] philPlace = new AffineTransform[NUMPHILS];
	int[] state = new int[NUMPHILS];
	double[] chopX = new double[NUMPHILS];
	double[] chopY = new double[NUMPHILS];
	boolean[] untable = new boolean[NUMPHILS];

	boolean frozen = false;
	Image offscreen;
	Dimension offscreensize;
	Graphics2D offgraphics;

	public PhilCanvas() {
		loadImages();
		initPlacing();
	}

	private void loadImages() {
		try {
			imgs[0] = ImageIO.read(Objects.requireNonNull(PhilCanvas.class.getClassLoader().getResource("thinking.gif")));
			imgs[1] = ImageIO.read(Objects.requireNonNull(PhilCanvas.class.getClassLoader().getResource("hungry.gif")));
			imgs[2] = ImageIO.read(Objects.requireNonNull(PhilCanvas.class.getClassLoader().getResource("gotright.gif")));
			imgs[3] = ImageIO.read(Objects.requireNonNull(PhilCanvas.class.getClassLoader().getResource("eating.gif")));
			imgs[4] = ImageIO.read(Objects.requireNonNull(PhilCanvas.class.getClassLoader().getResource("gotleft.gif")));
		} catch (IOException e) {
			System.out.println("Couldn't load one of the images");
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		backdrop();
		for (int i = 0; i < NUMPHILS; i++) {
			philPaint(offgraphics, i);
		}
		drawtable();
		if (deadlocked()) {
			offgraphics.setColor(Color.black);
			offgraphics.drawString("DEADLOCKED", -60, 0);
		}
		g.drawImage(offscreen, 0, 0, this);
	}

	private void backdrop() {
		Dimension d = getSize();
		if (offscreen == null || d.width != offscreensize.width || d.height != offscreensize.height) {
			offscreen = createImage(d.width, d.height);
			offscreensize = d;
			offgraphics = (Graphics2D) offscreen.getGraphics();
			offgraphics.setFont(new Font("Helvetica", Font.BOLD, 18));
			offgraphics.translate(d.width / 2, d.height / 2); // set origin to center
		}
		offgraphics.setColor(Color.lightGray);
		offgraphics.fillRect(-d.width / 2, -d.height / 2, d.width, d.height);
	}

	private void drawtable() {
		offgraphics.setColor(Color.red);
		offgraphics.fillOval(-65, -65, 130, 130);  // Increased the table size
		offgraphics.setColor(Color.black);
		for (int i = 0; i < NUMPHILS; i++) {
			if (untable[i]) offgraphics.fillOval((int) chopX[i], (int) chopY[i], 10, 10);
		}
	}

	private void philPaint(Graphics2D g, int i) {
		g.drawImage(imgs[state[i]], philPlace[i], this);
	}

	public synchronized void setPhil(int id, int s) throws InterruptedException {
		while (frozen) wait();
		state[id] = s;
		repaint();
	}

	public synchronized void freeze() {
		frozen = true;
	}

	public synchronized void thaw() {
		frozen = false;
		notifyAll();
	}

	public synchronized void setFork(int id, boolean taken) {
		untable[id] = !taken;
	}

	public boolean deadlocked() {
		int i = 0;
		while (i < NUMPHILS && state[i] == GOTRIGHT) ++i;
		return i == NUMPHILS;
	}

	void initPlacing() {
		double radius = 150.0;  // Increased the radius for the philosophers' placement
		double philWidth = imgs[0].getWidth();
		double philHeight = imgs[0].getHeight();
		double radians;

		for (int i = 0; i < NUMPHILS; i++) {
			philPlace[i] = new AffineTransform();
			radians = 2.0 * Math.PI * (1.0 - (double) i / (double) NUMPHILS);
			philPlace[i].rotate(radians);
			philPlace[i].translate(0, -radius);
			philPlace[i].translate(-philWidth / 2, -philHeight / 2);
		}

		radius = 50;  // Increased the radius for the chopstick placement
		for (int i = 0; i < NUMPHILS; i++) {
			radians = (double) i * 2.0 * Math.PI / (double) NUMPHILS + Math.PI / (double) NUMPHILS;
			chopX[i] = -Math.sin(radians) * radius - 5;
			chopY[i] = -Math.cos(radians) * radius - 5;
			untable[i] = true;
		}
	}
}
