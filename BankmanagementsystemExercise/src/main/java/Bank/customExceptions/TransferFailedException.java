package Bank.customExceptions;

public class TransferFailedException extends Exception {
    public TransferFailedException(String message) {
        super(message);
    }
}
