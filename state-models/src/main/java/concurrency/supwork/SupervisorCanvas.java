package concurrency.supwork;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class SupervisorCanvas extends JPanel {
	public static final int N_SLICE = 32; // Number of curve slices
	public static final int X_MAX = 256;
	public static final int Y_MAX = 200;

	private final String title;
	private final Font titleFont = new Font("Helvetica", Font.ITALIC | Font.BOLD, 24);
	private final Font areaFont = new Font("Helvetica", Font.BOLD, 18);

	private final boolean[] drawSlice = new boolean[N_SLICE];
	private final Color[] colorSlice = new Color[N_SLICE];

	private double area = 0.0;
	private Function func = null;

	public SupervisorCanvas(String title, Color bgColor) {
		this.title = title;
		setBackground(bgColor);
		Arrays.fill(drawSlice, false);
		setPreferredSize(new Dimension(X_MAX + 50, Y_MAX + 100));
	}

	// Display rectangle i with color c, add a to area field
	synchronized void setSlice(int i, double a) {
		drawSlice[i] = true;
		area += a;
		repaint();
	}

	// Reset display to clear rectangles and draw curve for f
	public synchronized void reset(Function f) {
		func = f;
		Arrays.fill(drawSlice, false);
		area = 0.0;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawContent(g);
	}

	private void drawContent(Graphics g) {
		Dimension size = getSize();

		// Clear background
		g.setColor(getBackground());
		g.fillRect(0, 0, size.width, size.height);

		// Draw title
		g.setColor(Color.black);
		g.setFont(titleFont);
		FontMetrics fm = g.getFontMetrics();
		int titleWidth = fm.stringWidth(title);
		int titleHeight = fm.getHeight();
		int x = (size.width - titleWidth) / 2;
		g.drawString(title, x, titleHeight);

		// Draw function
		x = (size.width - X_MAX) / 2;
		drawFunction(g, x, titleHeight + 20);

		// Draw area
		g.setColor(Color.black);
		g.setFont(areaFont);
		fm = g.getFontMetrics();
		String areaText = "Area: " + String.format("%.2f", area);
		int areaWidth = fm.stringWidth(areaText);
		int areaHeight = fm.getHeight();
		int x1 = (size.width - areaWidth) / 2;
		int y1 = titleHeight + Y_MAX + areaHeight;
		g.drawString(areaText, x1, y1 + 25);
		g.drawString("0.0", x - fm.stringWidth("0.0") / 2, y1 + 20);
		g.drawString("1.0", x + X_MAX - fm.stringWidth("1.0") / 2, y1 + 20);
	}

	private void drawFunction(Graphics g, int x, int y) {
		g.setColor(Color.white);
		g.fillRect(x, y, X_MAX, Y_MAX);

		// Draw ruler
		g.setColor(Color.black);
		g.drawLine(x - 1, y, x - 1, y + Y_MAX);
		g.drawLine(x - 2, y, x - 2, y + Y_MAX);
		g.drawLine(x, y + Y_MAX, x + X_MAX, y + Y_MAX);
		g.drawLine(x, y + Y_MAX + 1, x + X_MAX, y + Y_MAX + 1);
		for (int i = x; i <= X_MAX + x; i += 32) {
			g.drawLine(i, y + Y_MAX, i, y + Y_MAX + 5);
		}

		if (func == null) return;

		double scaleY = Y_MAX / Math.max(func.fn(1.0), func.fn(0.0));
		double deltaX = 1.0 / X_MAX;

		// Draw computed areas
		for (int i = 0; i < N_SLICE; ++i) {
			if (drawSlice[i]) {
				g.setColor(colorSlice[i]);
				int delta = X_MAX / N_SLICE;
				int xPos = i * delta;
				int yLen = (int) (func.fn(deltaX * (xPos + (double) delta / 2)) * scaleY);
				g.fillRect(x + xPos, y + Y_MAX - yLen, delta, yLen);
			}
		}

		// Draw curve
		g.setColor(Color.black);
		for (int i = 0; i < X_MAX; i++) {
			double dx = deltaX * i;
			int yStart = y + Y_MAX - (int) (func.fn(dx) * scaleY);
			int yEnd = y + Y_MAX - (int) (func.fn(dx + deltaX) * scaleY);
			g.drawLine(x + i, yStart, x + i + 1, yEnd);
		}
	}
}