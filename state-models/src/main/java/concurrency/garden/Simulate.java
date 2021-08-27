package concurrency.garden;

public class Simulate {

	public static void HWinterrupt() {
		if (Math.random() < 0.5)
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
	}
}