package at.bbrz.spring_exercise.exceptions;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException(String msg) {
        super(msg);
    }
}
