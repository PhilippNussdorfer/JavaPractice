package Bank.commandInterpretor;

import Bank.Bundle;
import Bank.customExceptions.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Interpreter {
    @Getter
    private final List<Command> commands = new ArrayList<>();

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
                     LoginFailedException | TransferFailedException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    public void getHelpMessage() {
        for (Command command : commands) {
            if (command instanceof HelpCommand) {
                try {
                    command.execute(null);
                } catch (InvalidUserException | TransferFailedException | InvalidInputException | LoginFailedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void setBundleForCommands(Bundle bundle) {
        for (Command command : commands) {
            command.setBundle(bundle);
        }
    }
}
