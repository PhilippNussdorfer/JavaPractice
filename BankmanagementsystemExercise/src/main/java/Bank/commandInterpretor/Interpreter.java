package Bank.commandInterpretor;

import Bank.Bundle;
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
        var params = input.split(" ");

        for (Command command : commands) {
            if (command.canHandle(params[0])) {
                command.execute(params);
                return;
            }
        }

        commands.get(0).execute(params);
    }

    public void getHelpMessage() {
        for (Command command : commands) {
            if (command instanceof HelpCommand)
                command.execute(null);
        }
    }

    public void addBundle(Bundle bundle) {
        for (Command command : commands) {
            command.addBundle(bundle);
        }
    }
}
