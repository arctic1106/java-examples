package concurrency;

import concurrency.message.Channel;
import concurrency.message.Receiver;
import concurrency.message.Sender;
import concurrency.message.SlotCanvas;
import concurrency.threads.ThreadPanel;

import javax.swing.*;
import java.awt.*;

public class SynchMsgDemo extends JFrame {
	private ThreadPanel tx;
	private ThreadPanel rx;
	private SlotCanvas senddisp;
	private SlotCanvas recvdisp;

	public SynchMsgDemo() {
		initUI();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SynchMsgDemo demo = new SynchMsgDemo();
			demo.setVisible(true);
		});
	}

	private void initUI() {
		setTitle("Synchronous Message Demo");
		setSize(600, 400); // Set the desired size for the window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // Center the window

		setLayout(new BorderLayout());

		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		panel.add(tx = new ThreadPanel("Sender", Color.blue, false));
		panel.add(senddisp = new SlotCanvas("e", Color.cyan, 1));
		panel.add(recvdisp = new SlotCanvas("v", Color.cyan, 1));
		panel.add(rx = new ThreadPanel("Receiver", Color.blue, false));

		add(panel, BorderLayout.CENTER);
		setBackground(Color.lightGray);

		// Start the threads after the UI is set up
		start();
	}

	private void start() {
		Channel<Integer> chan = new Channel<>();
		tx.start(new Sender(chan, senddisp));
		rx.start(new Receiver(chan, recvdisp));
	}

	private void stop() {
		tx.stop();
		rx.stop();
	}
}