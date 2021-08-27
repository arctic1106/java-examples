package concurrency;

import concurrency.message.Client;
import concurrency.message.Entry;
import concurrency.message.Server;
import concurrency.message.SlotCanvas;
import concurrency.threads.ThreadPanel;

import javax.swing.*;
import java.awt.*;

public class EntryDemo extends JFrame {
	private ThreadPanel clA, clB, sv;
	private SlotCanvas clientAdisp, clientBdisp, serverdisp;

	public EntryDemo() {
		initUI();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			EntryDemo demo = new EntryDemo();
			demo.setVisible(true);
		});
	}

	private void initUI() {
		setTitle("Entry Demo");
		setSize(600, 400); // Set the desired size for the window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // Center the window

		setLayout(new BorderLayout());

		JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER, 80, 5));
		top.setBackground(Color.cyan);
		JPanel bot = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

		top.add(clientAdisp = new SlotCanvas("req A", Color.cyan, 1));
		top.add(serverdisp = new SlotCanvas("v", Color.cyan, 1));
		top.add(clientBdisp = new SlotCanvas("req B", Color.cyan, 1));

		bot.add(clA = new ThreadPanel("Client A", Color.magenta, false));
		bot.add(sv = new ThreadPanel("Server", Color.blue, true));
		bot.add(clB = new ThreadPanel("Client B", Color.yellow, false));

		add(top, BorderLayout.CENTER);
		add(bot, BorderLayout.SOUTH);

		setBackground(Color.lightGray);

		// Start the threads after the UI is set up
		start();
	}

	private void start() {
		Entry<String, String> entry = new Entry<>();
		clA.start(new Client(entry, clientAdisp, "A"));
		clB.start(new Client(entry, clientBdisp, "B"));
		sv.start(new Server(entry, serverdisp));
	}

	private void stop() {
		clA.stop();
		clB.stop();
		sv.stop();
	}
}