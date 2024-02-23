package bbrz.textadventure.customException;

public class NoItemFoundException extends Exception {
    NoItemFoundException(String errMsg) {
        super(errMsg);
    }
}
