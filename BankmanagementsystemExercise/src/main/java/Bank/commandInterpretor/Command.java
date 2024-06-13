package Bank.commandInterpretor;

import Bank.Bundle;

public interface Command {
    boolean canHandle(String command);
    void execute(String[] params);
    String help();
    void addBundle(Bundle bundle);
}