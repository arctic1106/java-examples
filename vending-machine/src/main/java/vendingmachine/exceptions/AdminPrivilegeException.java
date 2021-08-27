package vendingmachine.exceptions;

public class AdminPrivilegeException extends RuntimeException {

    public AdminPrivilegeException(String errorType) {
        super(errorType);
    }
}
