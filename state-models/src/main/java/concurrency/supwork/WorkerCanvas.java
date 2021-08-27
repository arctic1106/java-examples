package concurrency.supwork;

import javax.swing.*;
import java.awt.*;

public class WorkerCanvas extends JPanel {
	private static final Font FONT = new Font("Helvetica", Font.BOLD, 18);
	private final JLabel value = new JLabel("       ");

	public WorkerCanvas(Color workerColor) {
		setBackground(workerColor);
		setLayout(new GridLayout(1, 2));

		JLabel title = new JLabel("Task:");
		title.setFont(FONT);
		value.setFont(FONT);
		value.setOpaque(true);  // Ensures the background color is shown
		value.setBackground(Color.LIGHT_GRAY);

		add(title);
		add(value);
	}

	// display current task number val
	synchronized void setTask(int val) {
		value.setText("   " + val + "   ");
	}
}
