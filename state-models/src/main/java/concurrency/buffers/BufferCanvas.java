package concurrency.buffers;

import java.awt.*;

public class BufferCanvas extends Canvas {
	private final String title;
	int slots;
	int in = 0;
	int out = 0;
	char[] buf = {' ', ' ', ' ', ' ', ' '};

	Font f1 = new Font("Helvetica", Font.ITALIC + Font.BOLD, 24);
	Font f2 = new Font("TimesRoman", Font.BOLD, 36);
	Image offscreen;
	Dimension offscreensize;
	Graphics offgraphics;

	public BufferCanvas(String title, int slots) {
		super();
		this.title = title;
		this.slots = slots;
		setSize(20 + 50 * this.slots, 150);
		setBackground(Color.cyan);
	}

	public void setvalue(char[] buf, int in, int out) {
		this.buf = buf;
		this.in = in;
		this.out = out;
		repaint();
	}

	public void paint(Graphics g) {
		update(g);
	}

	public synchronized void update(Graphics g) {
		Dimension d = getSize();
		if ((offscreen == null) || (d.width != offscreensize.width)
				|| (d.height != offscreensize.height)) {
			offscreen = createImage(d.width, d.height);
			offscreensize = d;
			offgraphics = offscreen.getGraphics();
			offgraphics.setFont(getFont());
		}

		offgraphics.setColor(getBackground());
		offgraphics.fillRect(0, 0, d.width, d.height);

		// Display the title
		offgraphics.setColor(Color.black);
		offgraphics.setFont(f1);
		FontMetrics fm = offgraphics.getFontMetrics();
		int w = fm.stringWidth(title);
		int h = fm.getHeight();
		int x = (getSize().width - w) / 2;
		int y = h;
		offgraphics.drawString(title, x, y);
		offgraphics.drawLine(x, y + 3, x + w, y + 3);
		// Buffer Boxes
		y = getSize().height / 2 - 15;
		offgraphics.setColor(Color.white);
		offgraphics.fillRect(10, y, 50 * slots, 50);
		offgraphics.setColor(Color.black);
		offgraphics.setFont(f2);
		for (int i = 0; i < slots; i++) {
			offgraphics.drawRect(10 + 50 * i, y, 50, 50);
			offgraphics.drawChars(buf, i, 1, 25 + 50 * i, y + 35);
		}
		//Input and output Pointers
		offgraphics.setColor(Color.blue);
		offgraphics.fillOval(35 + 50 * in, y - 20, 15, 15);
		offgraphics.setColor(Color.yellow);
		offgraphics.fillOval(35 + 50 * out, y + 55, 15, 15);
		g.drawImage(offscreen, 0, 0, null);
	}
}