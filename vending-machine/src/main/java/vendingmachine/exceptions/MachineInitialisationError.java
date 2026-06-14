package vendingmachine.exceptions;

public class MachineInitialisationError extends RuntimeException {

    public MachineInitialisationError(String errorType) {
        super(errorType);
    }
}
