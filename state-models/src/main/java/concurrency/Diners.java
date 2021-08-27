package concurrency;

import concurrency.diners.FixedPhilosopher;
import concurrency.diners.Fork;
import concurrency.diners.PhilCanvas;
import concurrency.diners.Philosopher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Diners extends JFrame {
	public final PhilCanvas display;
	private final Thread[] phil = new Thread[PhilCanvas.NUMPHILS];
	private final Fork[] fork = new Fork[PhilCanvas.NUMPHILS];
	private final JSlider slider;
	private boolean fixed = false;

	public Diners() {
		setTitle("Dining Philosophers");
		setSize(600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		fixed = Boolean.parseBoolean(System.getProperty("Version", "false"));
		display = new PhilCanvas();
		display.setPreferredSize(new Dimension(300, 320));
		add(display, BorderLayout.CENTER);

		slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);

		JButton restart = new JButton("Restart");
		restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (display.deadlocked()) {
					stopPhilosophers();
					slider.setValue(50);
					startPhilosophers();
				}
				display.thaw();
			}
		});

		JButton freeze = new JButton("Freeze");
		freeze.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				display.freeze();
			}
		});

		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BorderLayout());
		controlPanel.add(slider, BorderLayout.CENTER);
		controlPanel.add(restart, BorderLayout.EAST);
		controlPanel.add(freeze, BorderLayout.WEST);
		add(controlPanel, BorderLayout.SOUTH);

		pack(); // Adjust the frame to fit the preferred size of its components
		setVisible(true); // Make the frame visible
	}

	private Thread createPhilosopher(int id, Fork left, Fork right) {
		if (fixed) return new FixedPhilosopher(this, id, left, right);
		else return new Philosopher(this, id, left, right);
	}

	public int sleepTime() {
		return (slider.getValue() * (int) (100 * Math.random()));
	}

	public int eatTime() {
		return (slider.getValue() * (int) (50 * Math.random()));
	}

	public void startPhilosophers() {
		for (int i = 0; i < PhilCanvas.NUMPHILS; ++i)
			fork[i] = new Fork(display, i);
		for (int i = 0; i < PhilCanvas.NUMPHILS; ++i) {
			phil[i] = createPhilosopher(i,
					fork[(i - 1 + PhilCanvas.NUMPHILS) % PhilCanvas.NUMPHILS],
					fork[i]);
			phil[i].start();
		}
	}

	public void stopPhilosophers() {
		for (Thread thread : phil) {
			if (thread != null) {
				thread.interrupt();
			}
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Diners().startPhilosophers();
			}
		});
	}
}