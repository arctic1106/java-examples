package vendingmachine.exceptions;

public class MachineSelectionException extends RuntimeException {

    public MachineSelectionException(String errorType) {
        super(errorType);
    }
}
