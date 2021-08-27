package concurrency.threads;

import java.awt.*;

public class ThreadPanel extends Panel {
	private final Scrollbar scrollbar;
	private final GraphicPanel graphicPanel;
	private final boolean hasSlider;
	private DisplayThread displayThread;

	public ThreadPanel(String title, Color color) {
		this(title, color, false);
	}

	public ThreadPanel(String title, Color color, boolean hasSlider) {
		this.hasSlider = hasSlider;
		setFont(new Font("Helvetica", Font.BOLD, 14));
		setLayout(new BorderLayout());

		Panel buttonPanel = new Panel();
		Button run = new Button("Run");
		Button pause = new Button("Pause");

		run.addActionListener(e -> {
			if (displayThread != null) {
				displayThread.activate();
			}
		});

		pause.addActionListener(e -> {
			if (displayThread != null) {
				displayThread.passivate();
			}
		});

		buttonPanel.add(run);
		buttonPanel.add(pause);
		add(buttonPanel, BorderLayout.SOUTH);

		graphicPanel = new GraphicPanel(title, color);
		graphicPanel.setColor(Color.RED);
		add(graphicPanel, BorderLayout.NORTH);

		scrollbar = new Scrollbar(Scrollbar.HORIZONTAL, 30, 10, 2, 58);
		if (hasSlider) {
			add(scrollbar, BorderLayout.CENTER);
		}

		scrollbar.addAdjustmentListener(e -> {
			if (displayThread != null) {
				displayThread.setSplit(scrollbar.getValue());
			}
		});

		setBackground(Color.LIGHT_GRAY);
	}

	public static boolean rotate() throws InterruptedException {
		return DisplayThread.rotate();
	}

	public static void rotate(int degrees) throws InterruptedException {
		for (int i = 0; i < degrees; i += 6) {
			DisplayThread.rotate();
		}
	}

	public static void setSegmentColor(Color color) {
		DisplayThread.setSegmentColor(color);
	}

	public void start(Runnable task) {
		displayThread = new DisplayThread(graphicPanel, task, 5, hasSlider ? scrollbar.getValue() : 0, true);
		displayThread.start();
	}

	public Thread start(Runnable task, boolean suspended) {
		displayThread = new DisplayThread(graphicPanel, task, 5, hasSlider ? scrollbar.getValue() : 0, suspended);
		displayThread.start();
		return displayThread;
	}

	public void stop() {
		if (displayThread != null) {
			displayThread.interrupt();
		}
	}
}