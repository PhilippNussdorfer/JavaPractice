package Bank.commandInterpretor;

import Bank.Bundle;

public class LogoutCommand extends CommandABS {

    public LogoutCommand(Bundle bundle, String ... commands) {
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