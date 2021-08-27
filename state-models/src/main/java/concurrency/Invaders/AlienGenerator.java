package concurrency.Invaders;

import concurrency.SpaceInvaders;
import concurrency.time.Timed;

import java.awt.*;

public class AlienGenerator implements Timed {
	public static Image alien;
	int maxX = SpaceInvaders.theDisplay.getSize().width - 1;
	int delay = 50;
	Sprite target;

	public AlienGenerator(Sprite target) {
		SpaceInvaders.theTicker.addTimed(this);
		this.target = target;
	}

	public void pretick() {
	}

	public void tick() {
		--delay;
		if (delay <= 0) {
			Alien a = new Alien(20 + (int) ((maxX - 20) * Math.random()));
			SpaceInvaders.detector.addAlien(a);
			delay = (int) (50 * Math.random());
		}
	}

	static class Alien extends Sprite {
		private final int dir;

		Alien(int x) {
			super(alien, x, 0, SpaceInvaders.theDisplay, SpaceInvaders.theTicker);
			setIncrement(1 + (int) (Math.random() * 10.0));
			int choice = (int) (Math.random() * 10.0);
			if (choice < 3) dir = SOUTHWEST;
			else if (choice < 7) dir = SOUTH;
			else dir = SOUTHEAST;
		}

		public void hit() {
			new Explosion(this);
			SpaceInvaders.score.alienHit();
			SpaceInvaders.detector.removeAlien(this);
			remove();
		}

		public void tick() {
			move(dir);
			if ((posY() >= AREA.height - height - 1) || (posX() >= AREA.width - width - 1)) {
				SpaceInvaders.detector.removeAlien(this);
				remove();
			}
		}
	}
}
