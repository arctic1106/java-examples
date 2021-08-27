package concurrency;

import concurrency.message.Areceiver;
import concurrency.message.Asender;
import concurrency.message.Port;
import concurrency.message.SlotCanvas;
import concurrency.threads.ThreadPanel;

import javax.swing.*;
import java.awt.*;

public class AsynchMsgDemo extends JFrame {
	private ThreadPanel tx1, tx2, rx;
	private SlotCanvas send1disp, send2disp, recvdisp;

	public AsynchMsgDemo() {
		initUI();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			AsynchMsgDemo demo = new AsynchMsgDemo();
			demo.setVisible(true);
		});
	}

	private void initUI() {
		setTitle("Asynchronous Message Demo");
		setSize(600, 400); // Set the desired size for the window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // Center the window

		setLayout(new BorderLayout());

		JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER, 80, 5));
		top.setBackground(Color.cyan);

		JPanel bot = new JPanel();
		bot.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

		top.add(send1disp = new SlotCanvas("e1", Color.cyan, 1));
		top.add(recvdisp = new SlotCanvas("v", Color.cyan, 1));
		top.add(send2disp = new SlotCanvas("e2", Color.cyan, 1));

		bot.add(tx1 = new ThreadPanel("Sender1", Color.blue, false));
		bot.add(rx = new ThreadPanel("Receiver", Color.blue, false));
		bot.add(tx2 = new ThreadPanel("Sender2", Color.blue, false));

		add(top, BorderLayout.CENTER);
		add(bot, BorderLayout.SOUTH);
		setBackground(Color.lightGray);

		// Start the threads after the UI is set up
		start();
	}

	private void start() {
		Port<Integer> port = new Port<>();
		tx1.start(new Asender(port, send1disp));
		tx2.start(new Asender(port, send2disp));
		rx.start(new Areceiver(port, recvdisp));
	}

	private void stop() {
		tx1.stop();
		tx2.stop();
		rx.stop();
	}
}