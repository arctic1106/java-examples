package concurrency.Invaders;

import concurrency.SpaceInvaders;
import concurrency.time.TimeManager;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SpaceShip extends Sprite {
	public static Image rocket;
	public static Image rocket_hit;
	int speed = 6;
	int hitpause = 0;
	boolean left = false;
	boolean right = false;
	boolean up = false;
	boolean down = false;

	public SpaceShip(int x, int y, SpriteCanvas disp, TimeManager tick) {
		super(rocket, x, y, disp, tick);
		setIncrement(speed);
		display.addKeyListener(new KeyPress());
	}

	public void hit() {
		hitpause = 10;
		setImage(rocket_hit);
		SpaceInvaders.score.shieldHit();
	}

	public void tick() {
		if (hitpause > 0) {
			--hitpause;
			if (hitpause == 0) setImage(rocket);
			return;
		}
		if (left) move(WEST);
		if (right) move(EAST);
		if (up) move(NORTH);
		if (down) move(SOUTH);
	}

	class KeyPress extends KeyAdapter {
		public void keyPressed(KeyEvent k) {
			int code = k.getKeyCode();
			System.out.println("Key " + code + " pressed"); //2013

			if (code != KeyEvent.VK_SPACE) {
				left = false; //added 2013
				right = false; //
				up = false; //
				down = false; //
			}

			if (code == KeyEvent.VK_LEFT)
				left = true;
			else if (code == KeyEvent.VK_RIGHT)
				right = true;
			else if (code == KeyEvent.VK_UP)
				up = true;
			else if (code == KeyEvent.VK_DOWN)
				down = true;
		}

		public void keyReleased(KeyEvent k) {
			int code = k.getKeyCode();
			System.out.println("Key " + code + " released"); //2013
			if (code == KeyEvent.VK_LEFT)
				left = false;
			else if (code == KeyEvent.VK_RIGHT)
				right = false;
			else if (code == KeyEvent.VK_UP)
				up = false;
			else if (code == KeyEvent.VK_DOWN)
				down = false;
		}
	}
}
