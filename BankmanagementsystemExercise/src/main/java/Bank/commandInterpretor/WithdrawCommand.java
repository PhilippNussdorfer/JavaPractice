package Bank.commandInterpretor;

import Bank.Bundle;

public class WithdrawCommand extends CommandABS {

    public WithdrawCommand(Bundle bundle, String ... commands) {
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
