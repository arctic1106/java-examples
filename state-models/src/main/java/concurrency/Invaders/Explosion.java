package concurrency.Invaders;

import java.awt.*;

public class Explosion extends Sprite {
	public static Image[] explosion;
	private int currentFrame = 0;

	Explosion(Sprite parent) {
		super(explosion[0], parent);
	}

	public void tick() {
		++currentFrame;
		if (currentFrame < explosion.length) setImage(explosion[currentFrame]);
		else remove();
	}
}