package vendingmachine.exceptions;

public class MachineStockException extends RuntimeException {

    public MachineStockException(String errorType) {
        super(errorType);
    }
}
