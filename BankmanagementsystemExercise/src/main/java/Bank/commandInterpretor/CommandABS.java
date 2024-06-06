package Bank.commandInterpretor;

import Bank.Bundle;
import Bank.tools.StringFormatter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class CommandABS implements Command {
    @Getter
    private final String[] commands;
    protected final Bundle bundle;
    protected final StringFormatter formatter = new StringFormatter();

    @Override
    public boolean canHandle(String command) {
        for (String listCommand : commands) {
            if (command.equalsIgnoreCase(listCommand))
                return true;
        }

        return false;
    }
}
