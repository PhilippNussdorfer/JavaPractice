package Bank.commandInterpretor;

import Bank.BankManagementSystem;
import Bank.tools.BundleFactory;

public class LogoutCommand extends CommandABS {

    public LogoutCommand(String ... commands) {
        super(commands);
    }

    @Override
    public void execute(String[] params) {
        bundle.getSystem().updateBundle(BundleFactory.logoutBundle(bundle));
    }

    @Override
    public String help() {
        return null;
    }
}
