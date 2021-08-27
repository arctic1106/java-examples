import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConcentricCirclesDrawOnClick {
	private static int radius = 10;
	private static boolean increasing = true;
	private static final List<ColoredCircle> circles = new ArrayList<>();

	private record ColoredCircle(int x, int y, int radius, Color color) {
	}

	void main() {
		SwingUtilities.invokeLater(ConcentricCirclesDrawOnClick::createAndShowMainPanel);
	}

	private static void createAndShowMainPanel() {
		var panel = getJPanel();
		panel.setPreferredSize(new Dimension(750, 550));
		panel.setBackground(Color.DARK_GRAY);

		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				var random = new Random();
				var randomColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
				circles.add(new ColoredCircle(e.getX(), e.getY(), random.nextInt(100), randomColor)); // Circle radius is set to 30
				panel.repaint();
			}
		});

		var timer = new Timer(50, _ -> {
			if (radius >= 270) increasing = false; // Reverse the direction when reaching max radius
			else if (radius <= 10) increasing = true; // Reverse the direction when reaching min radius

			radius += (increasing ? 2 : -2); // Increment or decrement the radius
			panel.repaint(); // Repaint the panel to reflect the changes
		});
		timer.start();

		var frame = new JFrame("Concentric Circles Simulation");
		frame.add(panel);
		frame.pack(); // Pack adjusts the frame to fit the preferred size of contents
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null); // Center the frame on the screen
		frame.setVisible(true);
	}

	private static JPanel getJPanel() {
		return new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g); // Clear previous drawings
				Graphics2D g2d = (Graphics2D) g;

				// Enable anti-aliasing for smooth rendering
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				// Set the pen width (e.g., 10.0f for thicker lines)
				var penWidth = 10.0f;
				g2d.setStroke(new BasicStroke(penWidth));

				// Calculate the center of the panel
				int centerX = getWidth() / 2;
				int centerY = getHeight() / 2;

				for (int r = radius; r > 0; r -= 20) {
					var hue = (float) r / 200;
					g2d.setBackground(Color.darkGray);
					g2d.setColor(Color.getHSBColor(hue, 0.8f, 0.8f));
					g2d.drawOval(centerX - r, centerY - r, 2 * r, 2 * r);
				}

				for (ColoredCircle circle : circles) {
					g2d.setColor(circle.color);
					g2d.drawOval(circle.x - circle.radius, circle.y - circle.radius, circle.radius * 2, circle.radius * 2);
				}
			}
		};
	}
}