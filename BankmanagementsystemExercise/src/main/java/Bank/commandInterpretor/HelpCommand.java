package Bank.commandInterpretor;

import Bank.Bundle;

public class HelpCommand extends CommandABS {

    public HelpCommand(String[] commands, Bundle bundle) {
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
