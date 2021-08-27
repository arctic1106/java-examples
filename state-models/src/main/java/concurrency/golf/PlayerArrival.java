package concurrency.golf;

import concurrency.GolfClub;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerArrival extends JPanel {
	private static final String PLAYER_NAMES = "abcdefghijklnopqrstuvxyz";
	private final GolfClub golfClub;
	private int nextNameIndex = 0;

	public PlayerArrival(GolfClub golfClub, int numberOfPlayers) {
		super();
		this.golfClub = golfClub;

		JButton[] playerButtons = new JButton[numberOfPlayers];
		for (int i = 0; i < playerButtons.length; i++) {
			playerButtons[i] = new JButton("New Player(" + (i + 1) + ")");
			playerButtons[i].addActionListener(new NewPlayerAction(i + 1));
			add(playerButtons[i]);
		}
	}

	private class NewPlayerAction implements ActionListener {
		private final int numberOfBalls;

		NewPlayerAction(int numberOfBalls) {
			this.numberOfBalls = numberOfBalls;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String playerName = PLAYER_NAMES.substring(nextNameIndex, nextNameIndex + 1);
			Thread playerThread = new Thread(new Player(golfClub, numberOfBalls, playerName));
			playerThread.start();
			nextNameIndex = (nextNameIndex + 1) % PLAYER_NAMES.length();
		}
	}
}