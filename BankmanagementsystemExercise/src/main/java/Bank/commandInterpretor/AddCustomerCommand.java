package Bank.commandInterpretor;

import Bank.Bundle;

public class AddCustomerCommand extends CommandABS {

    public AddCustomerCommand(String[] commands, Bundle bundle) {
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
