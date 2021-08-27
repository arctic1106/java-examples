package concurrency;

import concurrency.parcel.*;
import concurrency.time.TimeManager;
import concurrency.time.TimeStop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ParcelRouter extends JFrame implements ActionListener {
	private ParcelCanvas display;
	private JPanel red;
	private JPanel blue;
	private JPanel green;
	private Font ff = new Font("Serif", Font.BOLD, 14);
	private TimeManager ticker;
	private JScrollBar bar;
	private ParcelMover first;

	public ParcelRouter() {
		initUI();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			ParcelRouter router = new ParcelRouter();
			router.setVisible(true);
		});
	}

	private void initUI() {
		setTitle("Parcel Router");
		setSize(600, 400); // Set the desired size for the window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // Center the window
		setLayout(new BorderLayout());

		display = new ParcelCanvas(this);
		add(display, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 5));
		red = makeButton(Color.red);
		blue = makeButton(Color.blue);
		green = makeButton(Color.green);
		JPanel magenta = makeButton(Color.magenta);

		buttonPanel.add(red);
		buttonPanel.add(blue);
		buttonPanel.add(green);
		buttonPanel.add(magenta);
		add(buttonPanel, BorderLayout.SOUTH);

		bar = new JScrollBar(JScrollBar.VERTICAL, 50, 5, 5, 90);
		add(bar, BorderLayout.EAST);

		setBackground(Color.lightGray);

		// Start the simulation after setting up the UI
		start();
	}

	private JPanel makeButton(Color c) {
		JPanel b = new JPanel();
		b.setBackground(c);
		Dimension size = new Dimension(45, 25);
		b.setMinimumSize(size);
		b.setMaximumSize(size);
		b.setPreferredSize(size);
		b.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				ParcelRouter.this.actionPerformed(new ActionEvent(b, ActionEvent.ACTION_PERFORMED, "click"));
			}
		});
		return b;
	}

	private ParcelMover makeStage(ParcelMover left, ParcelMover right, int fallDir, int level, int gate) {
		Chute a = new Chute(16, fallDir);
		ticker.addTimed(a);
		SensorController s = new SensorController(level);
		Chute b = new Chute(15, fallDir);
		ticker.addTimed(b);
		Switch g = new Switch(12, fallDir, gate, display);
		ticker.addTimed(g);
		a.next = s;
		s.next = b;
		s.aSwitch = g;
		b.next = g;
		g.left = left;
		g.right = right;
		return a;
	}

	private ParcelMover makeDest(int fallDir, int destination) {
		Chute leg1 = new Chute(12, fallDir);
		ticker.addTimed(leg1);
		Chute leg2 = new Chute(12, fallDir);
		ticker.addTimed(leg2);
		Chute leg3 = new Chute(16, Parcel.DOWN);
		ticker.addTimed(leg3);
		Chute leg4 = new Chute(16, Parcel.DOWN);
		ticker.addTimed(leg4);
		DestinationBin bin = new DestinationBin(destination);
		ticker.addTimed(bin);
		leg1.next = leg2;
		leg2.next = leg3;
		leg3.next = leg4;
		leg4.next = bin;
		return leg1;
	}

	private void start() {
		ticker = new TimeManager(bar.getValue());
		bar.addAdjustmentListener(ticker);
		ParcelMover[] dest = new ParcelMover[4];
		dest[0] = makeDest(Parcel.LEFT, 0);
		dest[1] = makeDest(Parcel.RIGHT, 1);
		dest[2] = makeDest(Parcel.LEFT, 2);
		dest[3] = makeDest(Parcel.RIGHT, 3);
		ParcelMover left = makeStage(dest[0], dest[1], Parcel.LEFT, 0, 2);
		ParcelMover right = makeStage(dest[2], dest[3], Parcel.RIGHT, 0, 1);
		first = makeStage(left, right, Parcel.DOWN, 1, 0);
		ticker.addTimed(display);
		ticker.start();
	}

	private void stop() {
		if (ticker != null) {
			ticker.interrupt();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Parcel p = null;
		try {
			if (e.getSource() == red)
				first.enter(p = new Parcel(0, Color.red, display));
			else if (e.getSource() == blue)
				first.enter(p = new Parcel(1, Color.blue, display));
			else if (e.getSource() == green)
				first.enter(p = new Parcel(2, Color.green, display));
			else
				first.enter(p = new Parcel(3, Color.magenta, display));
		} catch (TimeStop s) {
			p.remove();
		}
	}

	public Font getFf() {
		return ff;
	}

	public void setFf(Font ff) {
		this.ff = ff;
	}
}