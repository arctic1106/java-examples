package concurrency.Invaders;

import concurrency.SpaceInvaders;
import concurrency.time.Timed;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MissileLauncher implements Timed {
	public static Image missile;
	boolean launchMissile = false;
	Sprite owner;

	public MissileLauncher(Sprite owner) {
		SpaceInvaders.theDisplay.addKeyListener(new KeyPress());
		SpaceInvaders.theTicker.addTimed(this);
		this.owner = owner;
	}

	public void pretick() {
	}

	public void tick() {
		if (launchMissile) {
			launchMissile = false;
			SpaceInvaders.detector.addMissile(new Missile(owner));
		}
	}

	class KeyPress extends KeyAdapter {
		public void keyPressed(KeyEvent k) {
			System.out.println("Key " + k.getKeyCode() + " pressed"); //2013
			if (k.getKeyCode() == KeyEvent.VK_SPACE)
				launchMissile = true;
		}
	}

	static class Missile extends Sprite {
		int speed = 10;

		Missile(Sprite owner) {
			super(missile, owner.centerX() - missile.getWidth(null) / 2, owner.posY(), owner);
			setIncrement(speed);
		}

		public void hit() {
			SpaceInvaders.detector.removeMissile(this);
			remove();
		}

		public void tick() {
			move(NORTH);
			if (posY() <= 0) {
				SpaceInvaders.detector.removeMissile(this);
				remove();
			}
		}
	}
}