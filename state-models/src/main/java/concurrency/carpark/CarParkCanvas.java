package concurrency.carpark;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class CarParkCanvas extends JPanel {
	String title;
	int slots;
	int spaces;
	boolean[] occupied;
	Image car;

	Font f1 = new Font("Helvetica", Font.ITALIC + Font.BOLD, 24);
	Font f2 = new Font("TimesRoman", Font.BOLD, 36);

	public CarParkCanvas(String title, int slots) {
		super();
		this.title = title;
		this.slots = slots;
		this.spaces = slots;
		this.occupied = new boolean[slots];
		for (int i = 0; i < slots; i++) {
			occupied[i] = false;
		}
		setPreferredSize(new Dimension(20 + 50 * slots, 150));
		setBackground(Color.cyan);

		// Load the car image using ImageIcon
		car = new ImageIcon(Objects.requireNonNull(CarParkCanvas.class.getClassLoader().getResource("car.gif"))).getImage();
	}

	public void setvalue(int spaces, boolean[] occupied) {
		this.spaces = spaces;
		this.occupied = occupied;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawCarPark(g);
	}

	private void drawCarPark(Graphics g) {
		Dimension d = getSize();

		// Display the title
		g.setColor(Color.black);
		g.setFont(f1);
		FontMetrics fm = g.getFontMetrics();
		int w = fm.stringWidth(title);
		int h = fm.getHeight();
		int x = (d.width - w) / 2;
		int y = h;
		g.drawString(title, x, y);
		g.drawLine(x, y + 3, x + w, y + 3);

		// CarPark Places
		y = h + 10;
		g.setColor(Color.white);
		g.fillRect(10, y, 50 * slots, 100);
		g.setColor(Color.black);
		for (int i = 0; i < slots; i++) {
			g.drawRect(10 + 50 * i, y, 50, 100);
		}

		g.setColor(Color.white);
		for (int i = 1; i < slots; i++) {
			g.drawLine(10 + 50 * i, y + 60, 10 + 50 * i, y + 99);
		}

		// Arrival gate
		if (spaces == 0)
			g.setColor(Color.black);
		else
			g.setColor(Color.white);
		g.fillRect(8, y + 60, 5, 39);

		// Departure gate
		if (spaces == slots)
			g.setColor(Color.black);
		else
			g.setColor(Color.white);
		g.fillRect(8 + 50 * slots, y + 60, 5, 39);

		// Display Cars
		g.setColor(Color.blue);
		for (int i = 0; i < slots; i++) {
			if (occupied[i]) {
				g.drawImage(car, 15 + 50 * i, y + 5, this);
			}
		}
	}
}