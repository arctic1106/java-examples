package concurrency;

import concurrency.threads.Master;
import concurrency.threads.SlotPanel;
import concurrency.threads.ThreadPanel;

import javax.swing.*;
import java.awt.*;

public class JoinDemo extends JFrame {
	private final ThreadPanel ms;
	private final ThreadPanel sl;
	private final SlotPanel slotPanel;

	public JoinDemo() {
		// Set up the frame
		setTitle("Join Demo");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// Initialize components
		ms = new ThreadPanel("Master", Color.blue, true);
		slotPanel = new SlotPanel("res", Color.cyan, 1);
		sl = new ThreadPanel("Slave", Color.blue, true);

		// Add components to the frame
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.add(ms);
		topPanel.add(slotPanel);
		topPanel.add(sl);

		add(topPanel, BorderLayout.CENTER);

		// Set background color
		getContentPane().setBackground(Color.lightGray);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JoinDemo demo = new JoinDemo();
			demo.setVisible(true);
			demo.start(); // Start the application logic
		});
	}

	public void start() {
		ms.start(new Master(sl, slotPanel));
	}

	public void stop() {
		ms.stop();
	}
}