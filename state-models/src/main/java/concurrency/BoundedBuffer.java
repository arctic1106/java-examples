package concurrency;

import concurrency.buffers.*;
import concurrency.threads.ThreadPanel;

import javax.swing.*;
import java.awt.*;

public class BoundedBuffer extends JFrame {
	ThreadPanel prod;
	ThreadPanel cons;
	BufferCanvas buffDisplay;

	public BoundedBuffer() {
		// Set up Display
		prod = new ThreadPanel("Producer", Color.blue);
		cons = new ThreadPanel("Consumer", Color.yellow);
		buffDisplay = new BufferCanvas("Buffer", 5);

		GridBagLayout gridbag = new GridBagLayout();
		setLayout(gridbag);
		GridBagConstraints gc = new GridBagConstraints();
		gc.anchor = GridBagConstraints.NORTH;
		gc.fill = GridBagConstraints.HORIZONTAL;  // Allow components to fill horizontally
		gc.weightx = 1.0;  // Distribute extra space equally

		// Set constraints and add components
		gridbag.setConstraints(buffDisplay, gc);
		gridbag.setConstraints(prod, gc);
		gridbag.setConstraints(cons, gc);

		add(prod);
		add(buffDisplay);
		add(cons);

		// Set background color
		getContentPane().setBackground(Color.lightGray);

		// Frame settings
		setTitle("Bounded Buffer");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		BoundedBuffer app = new BoundedBuffer();
		app.start();
	}

	public void start() {
		Buffer<Character> b = new DisplayBuffer(buffDisplay, 5);
		// Create and start Threads
		prod.start(new Producer(b));
		cons.start(new Consumer(b));
	}

	public void stop() {
		prod.stop();
		cons.stop();
	}
}