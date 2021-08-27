package concurrency.readwrite;

import concurrency.message.StringCanvas;
import concurrency.threads.ThreadPanel;

import javax.swing.*;
import java.awt.*;

public class ReadersWriters extends JFrame {
	private ThreadPanel read1;
	private ThreadPanel read2;
	private ThreadPanel write1;
	private ThreadPanel write2;
	private StringCanvas display;
	private String rwClass;

	public ReadersWriters() {
		initUI();
	}

	private void initUI() {
		rwClass = System.getProperty("rwClass", "ReadWriteSafe"); // Use System properties or another configuration approach

		setTitle("Readers Writers Example");
		setSize(800, 600); // Set a more appropriate size for the frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		display = new StringCanvas(rwClass);
		display.setPreferredSize(new Dimension(630, 100));
		add(display, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.setBackground(Color.lightGray);

		read1 = new ThreadPanel("Reader 1", Color.blue, true);
		read2 = new ThreadPanel("Reader 2", Color.blue, true);
		write1 = new ThreadPanel("Writer 1", Color.yellow, true);
		write2 = new ThreadPanel("Writer 2", Color.yellow, true);

		panel.add(read1);
		panel.add(read2);
		panel.add(write1);
		panel.add(write2);

		add(panel, BorderLayout.SOUTH);
		setVisible(true); // Make sure to set the frame visible
	}

	public void start() {
		DisplayReadWrite lock = switch (rwClass) {
			case "ReadWritePriority" -> new DisplayReadWrite(display, new ReadWritePriority());
			case "ReadWriteFair" -> new DisplayReadWrite(display, new ReadWriteFair());
			default -> new DisplayReadWrite(display, new ReadWriteSafe());
		};
		read1.start(new Reader(lock));
		read2.start(new Reader(lock));
		write1.start(new Writer(lock));
		write2.start(new Writer(lock));
	}

	public void stop() {
		read1.stop();
		read2.stop();
		write1.stop();
		write2.stop();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			ReadersWriters frame = new ReadersWriters();
			frame.start(); // Start the threads after the UI is visible
		});
	}
}