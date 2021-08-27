package concurrency;

import concurrency.cruise.CarSimulator;
import concurrency.cruise.Controller;
import concurrency.cruise.CruiseDisplay;

import javax.swing.*;
import java.awt.*;

public class CruiseControl extends JFrame {
	private CarSimulator car;
	private Controller control;

	public CruiseControl() {
		initUI();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			CruiseControl frame = new CruiseControl();
			frame.setVisible(true);
		});
	}

	private void initUI() {
		setTitle("Cruise Control");
		setSize(800, 600); // Set the desired size for the window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // Center the window
		setLayout(new BorderLayout());

		car = new CarSimulator();
		add(car, BorderLayout.CENTER);

		CruiseDisplay disp = new CruiseDisplay();
		add(disp, BorderLayout.EAST);

		control = new Controller(car, disp, true); // Assuming `isfixed` is true for demo

		// Initialize buttons
		JButton engineOn = new JButton("Engine On");
		engineOn.addActionListener(e -> {
			car.engineOn();
			control.engineOn();
		});

		JButton engineOff = new JButton("Engine Off");
		engineOff.addActionListener(e -> {
			car.engineOff();
			control.engineOff();
		});

		JButton accelerate = new JButton("Accelerate");
		accelerate.addActionListener(e -> {
			car.accelerate();
			control.accelerator();
		});

		JButton brake = new JButton("Brake");
		brake.addActionListener(e -> {
			car.brake();
			control.brake();
		});

		JButton on = new JButton("On");
		on.addActionListener(e -> control.on());

		JButton off = new JButton("Off");
		off.addActionListener(e -> control.off());

		JButton resume = new JButton("Resume");
		resume.addActionListener(e -> control.resume());

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(engineOn);
		buttonPanel.add(engineOff);
		buttonPanel.add(accelerate);
		buttonPanel.add(brake);
		buttonPanel.add(on);
		buttonPanel.add(off);
		buttonPanel.add(resume);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	public void stop() {
		car.engineOff(); // Kill engine thread
		control.engineOff();
	}
}