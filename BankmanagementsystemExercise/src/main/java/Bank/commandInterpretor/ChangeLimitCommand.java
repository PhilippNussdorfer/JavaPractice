package Bank.commandInterpretor;

import Bank.Bundle;

public class ChangeLimitCommand extends CommandABS {

    public ChangeLimitCommand(Bundle bundle, String ... commands) {
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
