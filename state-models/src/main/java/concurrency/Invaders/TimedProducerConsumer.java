package concurrency.Invaders;

import javax.swing.*;

public class TimedProducerConsumer extends JFrame {
	public TimedProducerConsumer() {
		setTitle("Timed Producer Consumer");
		setSize(800, 600); // Set the desired size for the window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // Center the window

		// Initialize the ProducerConsumer instance
		new ProducerConsumer();

		// Add other initialization code if needed
	}
}