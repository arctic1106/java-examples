package concurrency;

import concurrency.garden.Counter;
import concurrency.garden.SynchronizedCounter;
import concurrency.garden.Turnstile;
import concurrency.threads.NumberCanvas;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Garden extends JPanel {
	public static final int MAX = 20;
	private Turnstile west;
	private Turnstile east;
	private NumberCanvas counterD;
	private NumberCanvas westD;
	private NumberCanvas eastD;
	private JCheckBox fixit;

	public Garden() {
		initializeUI();
	}

	// Main method to run the panel in a JFrame
	public static void main(String[] args) {
		JFrame frame = new JFrame("Garden");
		Garden garden = new Garden();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(garden);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void initializeUI() {
		setBackground(Color.LIGHT_GRAY);

		// Set up Button and Checkbox
		JPanel buttonPanel = new JPanel();
		JButton goButton = new JButton("Go");
		goButton.setFont(new Font("Helvetica", Font.BOLD, 24));
		buttonPanel.add(goButton);

		goButton.addActionListener(e -> {
			if ((west == null && east == null) || (!Objects.requireNonNull(west).isRunning() && !east.isRunning())) {
				go();
			}
		});

		JPanel controlPanel = new JPanel(new BorderLayout());
		controlPanel.add(buttonPanel, BorderLayout.CENTER);
		fixit = new JCheckBox("Fix It");
		controlPanel.add(fixit, BorderLayout.EAST);

		// Set up Display
		JPanel displayPanel = new JPanel();
		counterD = new NumberCanvas("Counter");
		westD = new NumberCanvas("West", Color.GREEN);
		eastD = new NumberCanvas("East", Color.GREEN);

		counterD.setPreferredSize(new Dimension(150, 100));
		westD.setPreferredSize(new Dimension(100, 100));
		eastD.setPreferredSize(new Dimension(100, 100));

		displayPanel.add(westD);
		displayPanel.add(counterD);
		displayPanel.add(eastD);

		// Arrange the panel layout
		setLayout(new BorderLayout());
		add(displayPanel, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.SOUTH);
	}

	private void go() {
		Counter counter = fixit.isSelected() ? new SynchronizedCounter(counterD) : new Counter(counterD);
		west = new Turnstile(westD, counter);
		east = new Turnstile(eastD, counter);
		west.start();
		east.start();
	}

	// Method to check if the thread is running
	private boolean isRunning(Thread thread) {
		return thread != null && thread.isAlive();
	}
}