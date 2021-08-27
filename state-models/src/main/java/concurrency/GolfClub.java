package concurrency;

import concurrency.golf.*;
import concurrency.message.SlotCanvas;
import concurrency.message.StringCanvas;

import javax.swing.*;
import java.awt.*;

public class GolfClub extends JFrame {
	public final static int playTime = 10000;
	private final static int Nballs = 5;
	private final String allocatorClass;
	private SlotCanvas waiting;
	private SlotCanvas playing;
	private SlotCanvas starting;
	private SlotCanvas ending;
	private StringCanvas allocDisplay;
	private Allocator alloc;

	public GolfClub(String allocatorClass) {
		this.allocatorClass = allocatorClass;
		initUI();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			GolfClub golfClub = new GolfClub(null); // You can pass the allocator class name here
			golfClub.setVisible(true);
		});
	}

	private void initUI() {
		setLayout(new BorderLayout());
		setSize(700, 270);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		allocDisplay = new StringCanvas(allocatorClass == null ? "SimpleAllocator" : allocatorClass);
		allocDisplay.setPreferredSize(new Dimension(430, 80));

		starting = new SlotCanvas("new", Color.green, 1);
		ending = new SlotCanvas("end", Color.red, 1);

		JPanel top = new JPanel();
		top.setLayout(new BorderLayout());
		top.add(allocDisplay, BorderLayout.CENTER);
		top.add(starting, BorderLayout.WEST);
		top.add(ending, BorderLayout.EAST);
		add(top, BorderLayout.NORTH);

		waiting = new SlotCanvas("wait golf balls", Color.yellow, 5);
		playing = new SlotCanvas("playing", Color.green, 5);

		JPanel p1 = new JPanel();
		p1.add(waiting);
		p1.add(playing);
		add(p1, BorderLayout.CENTER);

		add(new PlayerArrival(this, Nballs), BorderLayout.SOUTH);
		setBackground(Color.lightGray);

		setAllocator();
	}

	private void setAllocator() {
		if (allocatorClass == null || allocatorClass.equals("SimpleAllocator"))
			alloc = new DisplayAllocator(5, allocDisplay, new SimpleAllocator(5));
		else if (allocatorClass.equals("FairAllocator"))
			alloc = new DisplayAllocator(5, allocDisplay, new FairAllocator(5));
		else if (allocatorClass.equals("BoundedOvertakingAllocator"))
			alloc = new DisplayAllocator(5, allocDisplay, new BoundedOvertakingAllocator(5, 3));
		else
			alloc = new DisplayAllocator(5, allocDisplay, new SimpleAllocator(5));
	}

	public void getGolfBalls(int n, String name) throws InterruptedException {
		String s = name + n;
		starting.enter(s);
		Thread.sleep(500);
		starting.leave(s);
		waiting.enter(s);
		alloc.get(n);
		waiting.leave(s);
		playing.enter(s);
	}

	public void relGolfBalls(int n, String name) throws InterruptedException {
		String s = name + n;
		alloc.put(n);
		playing.leave(s);
		ending.enter(s);
		Thread.sleep(500);
		ending.leave(s);
	}
}