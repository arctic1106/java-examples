package concurrency;

import concurrency.primes.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Primes extends JFrame {
	public static final int EOS = -1;
	private static final int N = 7; // number of filters
	private final Filter[] filter = new Filter[N];
	private PrimesCanvas display;
	private Generator gen;

	public Primes() {
		initUI();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Primes primes = new Primes();
			primes.setVisible(true);
		});
	}

	private void initUI() {
		setTitle("Prime Sieve");
		setSize(800, 600); // Set the desired size for the window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // Center the window
		setLayout(new BorderLayout());

		display = new PrimesCanvas("Primes Sieve", Color.cyan, N + 1);
		add(display, BorderLayout.CENTER);

		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

		JButton goNoBuf = new JButton("Go - unbuffered");
		goNoBuf.setFont(new Font("Helvetica", Font.BOLD, 24));
		goNoBuf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ended()) go();
			}
		});

		JButton goBuf = new JButton("Go - buffered");
		goBuf.setFont(new Font("Helvetica", Font.BOLD, 24));
		goBuf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ended()) go();
			}
		});

		controlPanel.add(goNoBuf);
		controlPanel.add(goBuf);
		add(controlPanel, BorderLayout.SOUTH);

		setBackground(Color.lightGray);
	}

	private void go() {
		display.clear();
		// Create channels
		ArrayList<Pipe<Integer>> pipes = new ArrayList<>();
		for (int i = 0; i < N; ++i) {
			pipes.add(new PipeImplBuf<>());
		}

		// Create threads
		gen = new Generator(pipes.get(0), display);
		for (int i = 0; i < N; ++i) {
			filter[i] = new Filter(pipes.get(i), i < N - 1 ? pipes.get(i + 1) : null, i + 1, display);
		}
		gen.start();
		for (int i = 0; i < N; ++i) {
			filter[i].start();
		}
	}

	private boolean ended() {
		for (int i = 0; i < N; ++i) {
			if (filter[i] != null && filter[i].isAlive()) return false;
		}
		return gen == null || !gen.isAlive();
	}

	public void stop() {
		for (int i = 0; i < N; ++i) {
			if (filter[i] != null && filter[i].isAlive()) {
				filter[i].interrupt();
			}
		}
		if (gen != null && gen.isAlive()) {
			gen.interrupt();
		}
		display.clear();
	}
}