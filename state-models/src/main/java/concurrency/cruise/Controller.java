package concurrency.cruise;

public class Controller {
	final static int INACTIVE = 0; // cruise controller states
	final static int ACTIVE = 1;
	final static int CRUISING = 2;
	final static int STANDBY = 3;
	private int controlState = INACTIVE; //initial state
	private final SpeedControl sc;
	private final boolean isfixed;

	public Controller(CarSpeed cs, CruiseDisplay disp, boolean b) {
		sc = new SpeedControl(cs, disp);
		isfixed = b;
	}

	public synchronized void brake() {
		if (controlState == CRUISING) {
			sc.disableControl();
			controlState = STANDBY;
		}
	}

	public synchronized void accelerator() {
		if (controlState == CRUISING) {
			sc.disableControl();
			controlState = STANDBY;
		}
	}

	public synchronized void engineOff() {
		if (controlState != INACTIVE) {
			if (isfixed && controlState == CRUISING) sc.disableControl(); //bugfix
			controlState = INACTIVE;
		}
	}

	public synchronized void engineOn() {
		if (controlState == INACTIVE) {
			sc.clearSpeed();
			controlState = ACTIVE;
		}
	}

	public synchronized void on() {
		if (controlState != INACTIVE) {
			sc.recordSpeed();
			sc.enableControl();
			controlState = CRUISING;
		}
	}

	public synchronized void off() {
		if (controlState == CRUISING) {
			sc.disableControl();
			controlState = STANDBY;
		}
	}

	public synchronized void resume() {
		if (controlState == STANDBY) {
			sc.enableControl();
			controlState = CRUISING;
		}
	}
}

