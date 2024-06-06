package Bank.commandInterpretor;

import Bank.Bundle;

public class LoginCommand extends CommandABS {

    public LoginCommand(String[] commands, Bundle bundle) {
        super(commands, bundle);
    }

    @Override
    public void execute(String[] params) {

    }

    @Override
    public String help() {
        return "";
    }
}
