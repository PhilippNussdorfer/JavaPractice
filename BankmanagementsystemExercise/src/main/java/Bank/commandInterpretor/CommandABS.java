package Bank.commandInterpretor;

import Bank.Bundle;
import Bank.tools.StringFormatter;
import lombok.Getter;

public abstract class CommandABS implements Command {
    @Getter
    private final String[] commands;
    protected Bundle bundle;
    protected final StringFormatter formatter = new StringFormatter();

    public CommandABS(String[] commands) {
        this.commands = commands;
    }

    @Override
    public boolean canHandle(String command) {
        for (String listCommand : commands) {
            if (command.equalsIgnoreCase(listCommand))
                return true;
        }

        return false;
    }

    @Override
    public void addBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
