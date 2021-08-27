package concurrency;

import concurrency.Invaders.*;
import concurrency.time.TimeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class SpaceInvaders extends JFrame implements ActionListener {
	public static SpriteCanvas theDisplay;
	public static TimeManager theTicker;
	static MissileLauncher launcher;
	static AlienGenerator aliens;
	public static CollisionDetector detector;
	public static SpaceShip ship;
	public static ScorePanel score;
	Font ff = new Font("Serif", Font.BOLD, 14);
	JScrollBar bar;
	JButton go;
	Image rocket, rocket_hit, backdrop;
	Image missile, alien;
	Image[] explosion = new Image[16];

	public SpaceInvaders() {
		initUI();
	}

	private void initUI() {
		setSize(480, 348);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		MediaTracker mt = new MediaTracker(this);
		backdrop = AssetLoader.loadImage("space.gif");
		rocket = AssetLoader.loadImage("rocket.gif");
		rocket_hit = AssetLoader.loadImage("rocket_hit.gif");
		missile = AssetLoader.loadImage("missile.gif");
		alien = AssetLoader.loadImage("alien.gif");
		for (int i = 0; i < explosion.length; ++i) {
			explosion[i] = AssetLoader.loadImage("explosion" + (i + 1) + ".gif");
		}

		mt.addImage(backdrop, 0);
		mt.addImage(rocket, 1);
		mt.addImage(rocket_hit, 2);
		mt.addImage(missile, 3);
		mt.addImage(alien, 4);
		for (int i = 0; i < explosion.length; ++i) {
			mt.addImage(explosion[i], 5 + i);
		}
		try {
			mt.waitForAll();
		} catch (InterruptedException e) {
			System.out.println("Couldn't load one of the images");
		}

		SpaceShip.rocket = rocket;
		SpaceShip.rocket_hit = rocket_hit;
		MissileLauncher.missile = missile;
		Explosion.explosion = explosion;
		AlienGenerator.alien = alien;

		theDisplay = new SpriteCanvas(backdrop);
		theDisplay.setPreferredSize(
				new Dimension(Objects.requireNonNull(backdrop).getWidth(null),
						backdrop.getHeight(null))
		);
		add(theDisplay, BorderLayout.CENTER);

		bar = new JScrollBar(JScrollBar.VERTICAL, 40, 1, 0, 200);
		add(bar, BorderLayout.EAST);

		JPanel p = new JPanel();
		go = new JButton("  New Game  ");
		go.addActionListener(this);
		go.setFont(ff);
		p.add(go);

		score = new ScorePanel(ff, 5, this);
		p.add(score);
		add(p, BorderLayout.SOUTH);

		startGame();
	}

	private void startGame() {
		score.clear();
		theDisplay.reset();
		theDisplay.requestFocus();
		theTicker = new TimeManager(40);
		bar.addAdjustmentListener(theTicker);
		theTicker.addTimed(theDisplay);
		theTicker.start();

		ship = new SpaceShip(
				theDisplay.getWidth() / 2,
				theDisplay.getHeight() - rocket.getHeight(null) - 1,
				theDisplay, theTicker);

		aliens = new AlienGenerator(ship);
		launcher = new MissileLauncher(ship);
		detector = new CollisionDetector();
	}

	public void stopGame() {
		if (theTicker != null) {
			theDisplay.endGame();
			theTicker.removeTimed(theDisplay);
			theTicker.removeTimed(aliens);
			theTicker.removeTimed(launcher);
			theTicker.removeTimed(detector);
			ship.remove();
			theTicker.interrupt();
			theTicker = null;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		stopGame();
		startGame();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SpaceInvaders game = new SpaceInvaders();
			game.setVisible(true);
		});
	}

	static class AssetLoader {
		public static Image loadImage(String path) {
			return new ImageIcon(
					Objects.requireNonNull(
							AssetLoader.class.getClassLoader().getResource(path),
							"Resource not found: " + path
					)
			).getImage();
		}
	}
}