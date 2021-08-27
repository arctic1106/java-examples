package concurrency;

import concurrency.message.*;
import concurrency.threads.ThreadPanel;

import javax.swing.*;
import java.awt.*;

public class SelectMsgDemo extends JFrame {
	private ThreadPanel arrivals;
	private ThreadPanel departures;
	private ThreadPanel carpark;
	private StringCanvas cardisp;

	public SelectMsgDemo() {
		initUI();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SelectMsgDemo demo = new SelectMsgDemo();
			demo.setVisible(true);
		});
	}

	private void initUI() {
		setTitle("Select Message Demo");
		setSize(600, 400); // Set the desired size for the window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // Center the window

		setLayout(new BorderLayout());

		JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		p.add(arrivals = new ThreadPanel("Arrivals", Color.blue, false));
		p.add(carpark = new ThreadPanel("CarPark", Color.blue, false));
		p.add(departures = new ThreadPanel("Departures", Color.blue, false));

		cardisp = new StringCanvas("Carpark State", Color.cyan);
		cardisp.setSize(470, 100);

		add(cardisp, BorderLayout.CENTER);
		add(p, BorderLayout.SOUTH);

		setBackground(Color.lightGray);

		// Start the threads after the UI is set up
		start();
	}

	private void start() {
		Channel<Signal> arrive = new Channel<>();
		Channel<Signal> depart = new Channel<>();
		arrivals.start(new MsgGate(arrive));
		departures.start(new MsgGate(depart));
		carpark.start(new MsgCarPark(arrive, depart, cardisp, 4));
	}

	private void stop() {
		arrivals.stop();
		departures.stop();
		carpark.stop();
	}
}