package concurrency;

import concurrency.connector.TupleSpace;
import concurrency.connector.TupleSpaceImpl;
import concurrency.supwork.*;

import javax.swing.*;
import java.awt.*;

public class SupervisorWorker extends JFrame {
	private final SupervisorCanvas display;
	private final WorkerCanvas red, green, yellow, blue;
	private final Font buttonFont = new Font("TimesRoman", Font.ITALIC, 18);
	private final Font titleFont = new Font("SanSerif", Font.ITALIC + Font.BOLD, 24);
	private Thread supervisor, redWork, greenWork, yellowWork, blueWork;

	public SupervisorWorker() {
		display = new SupervisorCanvas("Supervisor", Color.cyan);
		red = new WorkerCanvas(Color.red);
		green = new WorkerCanvas(Color.green);
		yellow = new WorkerCanvas(Color.yellow);
		blue = new WorkerCanvas(Color.blue);
		initUI();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SupervisorWorker frame = new SupervisorWorker();
			frame.setVisible(true);
		});
	}

	private void initUI() {
		setTitle("Supervisor Worker Demo");
		setSize(600, 330);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // Center the window
		setLayout(new BorderLayout());

		add(display, BorderLayout.CENTER);
		add(createButtonPanel(), BorderLayout.SOUTH);
		add(createWorkerPanel(), BorderLayout.EAST);
	}

	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

		JButton fn1 = new JButton("f(x) = 1 - x*x");
		fn1.setFont(buttonFont);
		fn1.addActionListener(e -> {
			if (tasksEnded()) startTasks(new OneMinusXsquared());
		});

		JButton fn2 = new JButton("f(x) = 1-x*x*x");
		fn2.setFont(buttonFont);
		fn2.addActionListener(e -> {
			if (tasksEnded()) startTasks(new OneMinusXcubed());
		});

		JButton fn3 = new JButton("f(x) = x*x+0.1");
		fn3.setFont(buttonFont);
		fn3.addActionListener(e -> {
			if (tasksEnded()) startTasks(new XsquaredPlusPoint1());
		});

		buttonPanel.add(fn1);
		buttonPanel.add(fn2);
		buttonPanel.add(fn3);

		return buttonPanel;
	}

	private JPanel createWorkerPanel() {
		JPanel workerPanel = new JPanel(new GridLayout(6, 1));
		workerPanel.setBackground(Color.cyan);

		JLabel title = new JLabel("Workers");
		title.setFont(titleFont);
		workerPanel.add(title);

		workerPanel.add(red);
		workerPanel.add(green);
		workerPanel.add(yellow);
		workerPanel.add(blue);

		return workerPanel;
	}

	private void startTasks(Function fn) {
		display.reset(fn);
		TupleSpace bag = new TupleSpaceImpl();

		redWork = new Worker(red, bag, fn);
		greenWork = new Worker(green, bag, fn);
		yellowWork = new Worker(yellow, bag, fn);
		blueWork = new Worker(blue, bag, fn);
		supervisor = new Supervisor(display, bag);

		redWork.start();
		greenWork.start();
		yellowWork.start();
		blueWork.start();
		supervisor.start();
	}

	private boolean tasksEnded() {
		return (redWork == null || !redWork.isAlive()) &&
				(greenWork == null || !greenWork.isAlive()) &&
				(yellowWork == null || !yellowWork.isAlive()) &&
				(blueWork == null || !blueWork.isAlive()) &&
				(supervisor == null || !supervisor.isAlive());
	}

	public void stop() {
		if (redWork != null && redWork.isAlive()) redWork.interrupt();
		if (greenWork != null && greenWork.isAlive()) greenWork.interrupt();
		if (yellowWork != null && yellowWork.isAlive()) yellowWork.interrupt();
		if (blueWork != null && blueWork.isAlive()) blueWork.interrupt();
		if (supervisor != null && supervisor.isAlive()) supervisor.interrupt();
	}

	@Override
	public void dispose() {
		stop(); // Stop all running threads before closing
		super.dispose();
	}
}