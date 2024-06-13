package Bank.commandInterpretor;

import Bank.Bundle;
import Bank.customExceptions.InvalidUserException;
import Bank.customExceptions.NoBundleException;

public interface Command {
    boolean canHandle(String command) throws NoBundleException;
    void execute(String[] params) throws InvalidUserException;
    String help();
    void addBundle(Bundle bundle);
}