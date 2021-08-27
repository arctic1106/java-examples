package concurrency.threads;

import javax.swing.*;
import java.awt.*;

public class SlotPanel extends JPanel {
	private static final String EMPTY = "  ";
	private final String title;
	private final int slots;
	private final String[] buf;
	private final Font f1 = new Font("Helvetica", Font.ITALIC | Font.BOLD, 24);
	private final Font f2 = new Font("TimesRoman", Font.BOLD, 36);

	public SlotPanel(String title, Color backgroundColor, int slots) {
		this.title = title;
		this.slots = slots;
		this.buf = new String[slots];
		for (int i = 0; i < buf.length; i++) {
			buf[i] = EMPTY;
		}
		setPreferredSize(new Dimension(20 + 50 * slots, 120));
		setBackground(backgroundColor);
	}

	private int index(String s) {
		for (int i = 0; i < buf.length; i++) {
			if (buf[i].equals(s)) {
				return i;
			}
		}
		return -1;
	}

	public synchronized void enter(String name) {
		int i = index(EMPTY);
		if (i >= 0) {
			buf[i] = name;
			repaint();
		}
	}

	public synchronized void leave(String name) {
		int i = index(name);
		if (i >= 0) {
			buf[i] = EMPTY;
			repaint();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension d = getSize();
		Graphics2D g2d = (Graphics2D) g;

		// Draw the title
		g2d.setColor(Color.BLACK);
		g2d.setFont(f1);
		FontMetrics fm = g2d.getFontMetrics();
		int w = fm.stringWidth(title);
		int h = fm.getHeight();
		int x = (d.width - w) / 2;
		int y = h;
		g2d.drawString(title, x, y);
		g2d.drawLine(x, y + 3, x + w, y + 3);

		// Draw buffer boxes
		y = d.height / 2 - 15;
		g2d.setColor(Color.WHITE);
		g2d.fillRect(10, y, 50 * slots, 50);
		g2d.setColor(Color.BLACK);
		g2d.setFont(f2);
		for (int i = 0; i < slots; i++) {
			g2d.drawRect(10 + 50 * i, y, 50, 50);
			if (!buf[i].equals(EMPTY)) {
				g2d.drawString(buf[i], 20 + 50 * i, y + 35);
			}
		}
	}
}