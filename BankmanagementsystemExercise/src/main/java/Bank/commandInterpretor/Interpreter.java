package Bank.commandInterpretor;

import Bank.Bundle;
import Bank.customExceptions.*;
import Bank.tools.StringFormatter;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Interpreter {
    @Getter
    private final List<Command> commands = new ArrayList<>();
    private final StringFormatter formatter;

    public Interpreter (StringFormatter formatter) {
        this.formatter = formatter;
    }

    public void addCommand(Command ... commands) {
        this.commands.addAll(Arrays.asList(commands));
    }

    public void interpret(String input) {
        if (input.trim().isEmpty()) {
            getHelpMessage();
            return;
        }

        var params = input.split(" ");

        for (Command command : commands) {
            try {
                if (command.canHandle(params[0])) {
                    command.execute(params);
                    return;
                }
            } catch (NoBundleException | NumberFormatException | InvalidUserException | InvalidInputException |
                     LoginFailedException | AccountTypeAlreadyExists | AccountTypeNotExisting | TransferFailedException exception) {
                formatter.outputWrapper(exception.getMessage());
            }
        }
    }

    public void getHelpMessage() {
        for (Command command : commands) {
            if (command instanceof HelpCommand) {
                ((HelpCommand)command).execute(null);
            }
        }
    }

    public void setBundleForCommands(Bundle bundle) {
        for (Command command : commands) {
            command.setBundle(bundle);
        }
    }
}
