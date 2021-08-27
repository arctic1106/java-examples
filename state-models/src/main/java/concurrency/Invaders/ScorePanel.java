package concurrency.Invaders;

import concurrency.SpaceInvaders;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {
	private final JLabel shield = new JLabel("       ");
	private final JLabel alien = new JLabel("       ");
	private int shields;
	private int aliens;
	private final int lives;
	private final SpaceInvaders game;

	public ScorePanel(Font font, int initShield, SpaceInvaders game) {
		this.game = game;
		lives = shields = initShield;
		aliens = 0;

		// Set up the layout
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5); // Add some padding

		// Add Shield label
		gbc.gridx = 0;
		gbc.gridy = 0;
		JLabel t1 = new JLabel(" Shield:");
		add(t1, gbc);
		t1.setFont(font);

		// Add Shield value
		gbc.gridx = 1;
		add(shield, gbc);
		shield.setFont(font);
		shield.setBackground(Color.lightGray);
		shield.setOpaque(true);

		// Add Aliens label
		gbc.gridx = 0;
		gbc.gridy = 1;
		JLabel t2 = new JLabel(" Aliens:");
		add(t2, gbc);
		t2.setFont(font);

		// Add Aliens value
		gbc.gridx = 1;
		add(alien, gbc);
		alien.setFont(font);
		alien.setBackground(Color.lightGray);
		alien.setOpaque(true);

		clear();
	}

	public void shieldHit() {
		--shields;
		shield.setText(String.format(" %3d ", shields));
		if (shields <= 0) {
			game.stopGame();
		}
	}

	public void alienHit() {
		++aliens;
		alien.setText(String.format(" %3d ", aliens));
	}

	public void clear() {
		shields = lives;
		aliens = 0;
		shield.setText(String.format(" %3d ", shields));
		alien.setText(String.format(" %3d ", aliens));
	}
}
