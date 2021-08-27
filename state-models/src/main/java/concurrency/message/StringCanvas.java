package concurrency.message;

import javax.swing.*;
import java.awt.*;

public class StringCanvas extends JPanel {
	private static final Font TITLE_FONT = new Font("Times", Font.ITALIC + Font.BOLD, 24);
	private static final Font VALUE_FONT = new Font("Helvetica", Font.BOLD, 36);
	private final String title;
	private String value = "";

	public StringCanvas(String title) {
		this(title, Color.CYAN);
	}

	public StringCanvas(String title, Color backgroundColor) {
		this.title = title;
		setBackground(backgroundColor);
	}

	public void setColor(Color color) {
		setBackground(color);
		repaint();
	}

	public void setString(String newValue) {
		this.value = newValue;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		drawTitle(g2d);
		drawValue(g2d);
	}

	private void drawTitle(Graphics2D g2d) {
		g2d.setFont(TITLE_FONT);
		g2d.setColor(Color.BLACK);

		FontMetrics fm = g2d.getFontMetrics();
		int titleWidth = fm.stringWidth(title);
		int titleHeight = fm.getHeight();

		int x = (getWidth() - titleWidth) / 2;
		int y = titleHeight;

		g2d.drawString(title, x, y);
		g2d.drawLine(x, y + 3, x + titleWidth, y + 3);
	}

	private void drawValue(Graphics2D g2d) {
		g2d.setFont(VALUE_FONT);
		g2d.setColor(Color.BLACK);

		FontMetrics fm = g2d.getFontMetrics();
		int valueWidth = fm.stringWidth(value);
		int valueHeight = fm.getHeight();

		int x = (getWidth() - valueWidth) / 2;
		int y = (getHeight() + valueHeight) / 2;

		g2d.drawString(value, x, y);
	}
}