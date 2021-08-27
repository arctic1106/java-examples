class Controller {
    final static int INACTIVE = 0; // cruise controller states
    final static int ACTIVE = 1;
    final static int CRUISING = 2;
    final static int STANDBY = 3;
    private int controlState = INACTIVE; //initial state
    private final SpeedControl sc;

    Controller(CarSpeed cs, CruiseDisplay disp) {
        sc = new SpeedControl(cs, disp);
    }

    synchronized void brake() {
        if (controlState == CRUISING) {
            sc.disableControl();
            controlState = STANDBY;
        }
    }

    synchronized void accelerator() {
        if (controlState == CRUISING) {
            sc.disableControl();
            controlState = STANDBY;
        }
    }

    synchronized void engineOff() {
        if (controlState != INACTIVE) {
            if (controlState == CRUISING) sc.disableControl();
            controlState = INACTIVE;
        }
    }

    synchronized void engineOn() {
        if (controlState == INACTIVE) {
            sc.clearSpeed();
            controlState = ACTIVE;
        }
    }

    synchronized void on() {
        if (controlState != INACTIVE) {
            sc.recordSpeed();
            sc.enableControl();
            controlState = CRUISING;
        }
    }

    synchronized void off() {
        if (controlState == CRUISING) {
            sc.disableControl();
            controlState = STANDBY;
        }
    }

    synchronized void resume() {
        if (controlState == STANDBY) {
            sc.enableControl();
            controlState = CRUISING;
        }
    }
}
