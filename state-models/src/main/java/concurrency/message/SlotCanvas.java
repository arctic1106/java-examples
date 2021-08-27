package concurrency.message;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class SlotCanvas extends JPanel {
	private final static String empty = "  ";
	String title_;
	int slots_;
	String[] buf_;
	Font f1 = new Font("Helvetica", Font.ITALIC + Font.BOLD, 24);
	Font f2 = new Font("TimesRoman", Font.BOLD, 36);

	public SlotCanvas(String title, Color c, int slots) {
		super();
		title_ = title;
		slots_ = slots;
		buf_ = new String[slots_];
		Arrays.fill(buf_, empty);
		setPreferredSize(new Dimension(20 + 50 * slots_, 120));
		setBackground(c);
	}

	private int index(String s) {
		for (int i = 0; i < buf_.length; i++)
			if (buf_[i].equals(s)) return i;
		return -1;
	}

	public synchronized void enter(String name) {
		int i = index(empty);
		if (i >= 0) buf_[i] = name;
		repaint();
	}

	public synchronized void leave(String name) {
		int i = index(name);
		if (i >= 0) buf_[i] = empty;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Double buffering is handled automatically by Swing
		Graphics2D g2d = (Graphics2D) g;
		Dimension d = getSize();

		// Display the title
		g2d.setColor(Color.black);
		g2d.setFont(f1);
		FontMetrics fm = g2d.getFontMetrics();
		int w = fm.stringWidth(title_);
		int h = fm.getHeight();
		int x = (getSize().width - w) / 2;
		int y = h;
		g2d.drawString(title_, x, y);
		g2d.drawLine(x, y + 3, x + w, y + 3);

		// Buffer Boxes
		y = getSize().height / 2 - 15;
		g2d.setColor(Color.white);
		g2d.fillRect(10, y, 50 * slots_, 50);
		g2d.setColor(Color.black);
		g2d.setFont(f2);
		for (int i = 0; i < slots_; i++) {
			g2d.drawRect(10 + 50 * i, y, 50, 50);
			if (buf_[i] != null)
				g2d.drawString(buf_[i], 20 + 50 * i, y + 35);
		}
	}
}