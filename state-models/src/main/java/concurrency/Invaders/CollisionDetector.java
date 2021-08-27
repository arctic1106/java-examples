package concurrency.Invaders;

import concurrency.SpaceInvaders;
import concurrency.time.ImmutableList;
import concurrency.time.Timed;

public class CollisionDetector implements Timed {
	ImmutableList<Sprite> aliens = null;
	ImmutableList<Sprite> missiles = null;

	public CollisionDetector() {
		SpaceInvaders.theTicker.addTimed(this);
	}

	public void addAlien(Sprite a) {
		aliens = ImmutableList.add(aliens, a);
	}

	public void removeAlien(Sprite a) {
		aliens = ImmutableList.remove(aliens, a);
	}

	public void addMissile(Sprite m) {
		missiles = ImmutableList.add(missiles, m);
	}

	public void removeMissile(Sprite m) {
		missiles = ImmutableList.remove(missiles, m);
	}

	public void pretick() {
		if (aliens != null)
			for (Sprite alien : aliens) {
				if (alien.collide(SpaceInvaders.ship)) {
					SpaceInvaders.ship.hit();
					alien.hit();
				}
				if (missiles != null)
					for (Sprite missile : missiles) {
						if (alien.collide(missile)) {
							alien.hit();
							missile.hit();
						}
					}
			}
	}

	public void tick() {
	}
}