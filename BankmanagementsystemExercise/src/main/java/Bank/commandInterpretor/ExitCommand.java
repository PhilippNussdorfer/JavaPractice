package Bank.commandInterpretor;

import Bank.Bundle;

public class ExitCommand extends CommandABS {

    public ExitCommand(Bundle bundle, String ... commands) {
        super(commands, bundle);
    }

    @Override
    public void execute(String[] params) {

    }

    @Override
    public String help() {
        return null;
    }
}
