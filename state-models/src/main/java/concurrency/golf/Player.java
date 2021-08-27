package concurrency.golf;

import concurrency.GolfClub;

class Player extends Thread {
	private final GolfClub gc;
	private final String name;
	private final int nballs;

	Player(GolfClub g, int n, String s) {
		gc = g;
		name = s;
		nballs = n;
	}

	public void run() {
		try {
			gc.getGolfBalls(nballs, name);
			sleep(GolfClub.playTime);
			gc.relGolfBalls(nballs, name);
		} catch (InterruptedException e) {
		}
	}
}