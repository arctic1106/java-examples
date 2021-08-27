package concurrency.threads;

import javax.swing.*;
import java.awt.*;

public class GraphicPanel extends JPanel {
	private static final int Cx = 30;
	private static final int Cy = 45;
	private final String title;
	private final Font f1 = new Font("Times", Font.ITALIC | Font.BOLD, 24);
	private int angle = 0;
	private Color arcColor = Color.BLUE;
	private int segStart = 9999;
	private int segEnd = 9999;
	private Color segColor = Color.YELLOW;

	public GraphicPanel(String title, Color arcColor) {
		this.title = title;
		this.arcColor = arcColor;
		setPreferredSize(new Dimension(150, 150));
	}

	public void setColor(Color color) {
		setBackground(color);
		repaint();
	}

	public void setAngle(int angle) {
		this.angle = angle;
		repaint();
	}

	public void setSegment(int start, int end, Color color) {
		this.segStart = start;
		this.segEnd = end;
		this.segColor = color;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension d = getSize();
		Graphics2D g2d = (Graphics2D) g;

		// Display the title
		g2d.setColor(Color.BLACK);
		g2d.setFont(f1);
		FontMetrics fm = g2d.getFontMetrics();
		int w = fm.stringWidth(title);
		int h = fm.getHeight();
		int x = (d.width - w) / 2;
		int y = h;
		g2d.drawString(title, x, y);
		g2d.drawLine(x, y + 3, x + w, y + 3);

		// Display the arc
		if (angle > 0) {
			g2d.setColor(arcColor);
			if (angle < segStart || segStart == segEnd) {
				g2d.fillArc(Cx, Cy, 90, 90, 0, angle);
			} else if (angle >= segStart && angle < segEnd) {
				g2d.fillArc(Cx, Cy, 90, 90, 0, segStart);
				if (angle - segStart > 0) {
					g2d.setColor(segColor);
					g2d.fillArc(Cx, Cy, 90, 90, segStart, angle - segStart);
				}
			} else {
				g2d.fillArc(Cx, Cy, 90, 90, 0, segStart);
				g2d.setColor(segColor);
				g2d.fillArc(Cx, Cy, 90, 90, segStart, segEnd - segStart);
				if (angle - segEnd > 0) {
					g2d.setColor(arcColor);
					g2d.fillArc(Cx, Cy, 90, 90, segEnd, angle - segEnd);
				}
			}
		}
	}
}