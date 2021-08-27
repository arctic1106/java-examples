package concurrency;

import concurrency.carpark.*;
import concurrency.threads.ThreadPanel;

import javax.swing.*;
import java.awt.*;

public class CarPark extends JFrame {
	final static int Places = 4;
	ThreadPanel arrivals;
	ThreadPanel departures;
	CarParkCanvas carDisplay;

	public CarPark() {
		// Set up Display
		arrivals = new ThreadPanel("Arrivals", Color.blue);
		departures = new ThreadPanel("Departures", Color.yellow);
		carDisplay = new CarParkCanvas("CarPark", Places);

		GridBagLayout gridbag = new GridBagLayout();
		setLayout(gridbag);
		GridBagConstraints gc = new GridBagConstraints();
		gc.anchor = GridBagConstraints.NORTH;
		gc.fill = GridBagConstraints.HORIZONTAL; // Allow components to fill horizontally
		gc.weightx = 1.0; // Distribute extra space equally

		// Set constraints and add components
		gridbag.setConstraints(carDisplay, gc);
		gridbag.setConstraints(arrivals, gc);
		gridbag.setConstraints(departures, gc);

		add(arrivals);
		add(carDisplay);
		add(departures);

		// Set background color
		getContentPane().setBackground(Color.lightGray);

		// Frame settings
		setTitle("Car Park");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		CarPark app = new CarPark();
		app.start();
	}

	public void start() {
		CarParkControl c = new DisplayCarPark(carDisplay, Places);
		arrivals.start(new Arrivals(c));
		departures.start(new Departures(c));
	}

	public void stop() {
		arrivals.stop();
		departures.stop();
	}
}