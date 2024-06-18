package Bank.commandInterpretor;

import Bank.Bundle;
import Bank.customExceptions.InvalidInputException;
import Bank.customExceptions.InvalidUserException;
import Bank.customExceptions.LoginFailedException;
import Bank.customExceptions.NoBundleException;

public interface Command {
    boolean canHandle(String command) throws NoBundleException;
    void execute(String[] params) throws InvalidUserException, InvalidInputException, LoginFailedException;
    String help();
    void setBundle(Bundle bundle);
}