package Bank.commandInterpretor;

import Bank.Bundle;
import Bank.customExceptions.NoBundleException;

public interface Command {
    boolean canHandle(String command) throws NoBundleException;
    void execute(String[] params);
    String help();
    void addBundle(Bundle bundle);
}