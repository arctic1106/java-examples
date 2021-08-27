package concurrency;

import concurrency.announce.BoxCanvas;
import concurrency.announce.BoxMover;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EventDemo extends JFrame {
	final static int MAX = 8;
	final static int FAST = 600;
	final static int SLOW = 1200;
	BoxCanvas display;
	Thread[] movers = new Thread[MAX];
	JButton goFast, goSlow, end;
	JLabel gameClicks;
	int clicks = 0;

	Font ff = new Font("Serif", Font.BOLD, 14);

	public EventDemo() {
		// Set up the layout and add components
		setLayout(new BorderLayout());
		display = new BoxCanvas();
		display.setPreferredSize(new Dimension(300, 240));
		display.addMouseListener(new MyListener());
		add(display, BorderLayout.CENTER);

		JPanel p = new JPanel();
		goFast = new JButton("Go Fast");
		goSlow = new JButton("Go Slow");
		gameClicks = new JLabel("  0  ");
		end = new JButton("End");

		p.add(goFast);
		p.add(goSlow);
		p.add(gameClicks);
		p.add(end);

		// Action listeners
		goFast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
				go(FAST);
			}
		});

		goSlow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
				go(SLOW);
			}
		});

		end.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});

		// Set font and background
		goFast.setFont(ff);
		goSlow.setFont(ff);
		end.setFont(ff);
		gameClicks.setFont(ff);
		gameClicks.setOpaque(true); // Make sure the background color is visible
		gameClicks.setBackground(Color.lightGray);
		getContentPane().setBackground(Color.magenta);
		add(p, BorderLayout.SOUTH);

		// Frame settings
		setTitle("Event Demo");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		new EventDemo();
	}

	public void go(int speed) {
		display.reset();
		clicks = 0;
		gameClicks.setText("  " + clicks + "  ");
		for (int i = 0; i < MAX; ++i) {
			movers[i] = new BoxMover(display, i, speed);
			movers[i].start();
		}
	}

	public void stop() {
		for (int i = 0; i < MAX; ++i) {
			if (movers[i] != null && movers[i].isAlive()) {
				movers[i].interrupt();
			}
		}
	}

	class MyListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			++clicks;
			gameClicks.setText("  " + clicks + "  ");
		}
	}
}