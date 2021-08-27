package concurrency.parcel;

import concurrency.time.Timed;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ParcelCanvas extends JPanel implements Timed {
	private static final int MAX = 16;
	private final JFrame parcelRouter;
	private final ArrayList<Parcel> boxes;
	private final Polygon[][] gate = new Polygon[2][3];
	private final int[] gatedir = new int[3];
	private Image router;
	private Image offscreen;
	private Dimension offscreensize;

	public ParcelCanvas(JFrame parcelRouter) {
		this.parcelRouter = parcelRouter;
		this.boxes = new ArrayList<>();
		loadImages();
		setPreferredSize(new Dimension(router.getWidth(null), router.getHeight(null)));
		Arrays.fill(gatedir, 0);
		initializeGates();
	}

	private void loadImages() {
		MediaTracker mt = new MediaTracker(this);
		router = parcelRouter.getContentPane().getToolkit().getImage(ParcelCanvas.class.getClassLoader().getResource("router.gif"));
		mt.addImage(router, 0);

		try {
			mt.waitForID(0);
		} catch (InterruptedException e) {
			System.out.println("Couldn't load one of the images");
		}
	}

	private void initializeGates() {
		gate[0][0] = genGate(193, 127, 1);
		gate[1][0] = genGate(193, 127, -1);
		gate[0][1] = genGate(280, 214, 1);
		gate[1][1] = genGate(280, 214, -1);
		gate[0][2] = genGate(107, 212, 1);
		gate[1][2] = genGate(107, 212, -1);
	}

	// Clear all boxes
	void reset() {
		boxes.clear();
	}

	void setGate(int id, int dir) {
		gatedir[id] = dir;
	}

	public void addParcel(Parcel p) {
		boxes.add(p);
	}

	public void removeParcel(Parcel p) {
		boxes.remove(p);
	}

	@Override
	public void pretick() {
		repaint();
	}

	@Override
	public void tick() {
		// No implementation needed in this context
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawContent(g);
	}

	private void drawContent(Graphics g) {
		if (offscreen == null || getWidth() != offscreensize.width || getHeight() != offscreensize.height) {
			offscreen = createImage(getWidth(), getHeight());
			offscreensize = new Dimension(getWidth(), getHeight());
		}

		Graphics offgraphics = offscreen.getGraphics();
		offgraphics.drawImage(router, 0, 0, this);

		// Display gates
		offgraphics.setColor(Color.orange);
		for (int i = 0; i < gatedir.length; ++i) {
			offgraphics.fillPolygon(gate[gatedir[i]][i]);
		}

		// Display boxes
		for (Parcel b : boxes) {
			if (b.displayIt) {
				offgraphics.setColor(b.c);
				offgraphics.fillRect(b.x, b.y, Parcel.SIZE, Parcel.SIZE);
			}
		}

		g.drawImage(offscreen, 0, 0, this);
	}

	private Polygon genGate(int x, int y, int sign) {
		int[] xp = {x, x + 40 * sign, x + 40 * sign, x};
		int[] yp = {y, y - 40, y - 35, y + 5};
		return new Polygon(xp, yp, 4);
	}
}
