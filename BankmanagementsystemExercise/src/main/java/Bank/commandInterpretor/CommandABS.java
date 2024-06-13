package Bank.commandInterpretor;

import Bank.Bundle;
import Bank.customExceptions.NoBundleException;
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
    public boolean canHandle(String command) throws NoBundleException {
        for (String listCommand : commands) {
            if (bundle == null)
                throw new NoBundleException("Bundle is missing please make sure it is added");

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
