package Bank.commandInterpretor;

import Bank.BankManagementSystem;
import Bank.tools.BundleFactory;

public class LogoutCommand extends CommandABS {

    public LogoutCommand(String ... commands) {
        super(commands);
    }

    @Override
    public void execute(String[] params) {
        BankManagementSystem.updateBundle(BundleFactory.startingBundle());
    }

    @Override
    public String help() {
        return null;
    }
}
