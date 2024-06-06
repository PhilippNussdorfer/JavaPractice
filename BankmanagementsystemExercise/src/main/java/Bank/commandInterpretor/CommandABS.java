package Bank.commandInterpretor;

import Bank.Bundle;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class CommandABS implements Command {
    private final String[] commands;
    private final Bundle bundle;

    @Override
    public boolean canHandle(String command) {
        for (String listCommand : commands) {
            if (command.equalsIgnoreCase(listCommand))
                return true;
        }

        return false;
    }
}
