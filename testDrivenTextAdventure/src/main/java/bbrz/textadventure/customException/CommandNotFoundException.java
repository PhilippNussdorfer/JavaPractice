package bbrz.textadventure.customException;

public class CommandNotFoundException extends Exception {

    public CommandNotFoundException(String errorMsg) {
        super(errorMsg);
    }
}
