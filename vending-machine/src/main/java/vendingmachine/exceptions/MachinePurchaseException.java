package vendingmachine.exceptions;

public class MachinePurchaseException extends RuntimeException {

    public MachinePurchaseException(String errorType) {
        super(errorType);
    }
}
