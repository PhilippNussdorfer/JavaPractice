package Bank.customExceptions;

public class LoginFailedException extends Exception {
    public LoginFailedException(String message) {
        super(message);
    }
}
