package bbrz.textadventure.customException;

public class NoItemFoundException extends Exception {
    public NoItemFoundException(String errMsg) {
        super(errMsg);
    }
}
