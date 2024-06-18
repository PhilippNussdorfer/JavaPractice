package Bank.commandInterpretor;

import Bank.tools.BundleFactory;

public class LogoutCommand extends CommandAbstract {

    public LogoutCommand(String ... commands) {
        super(commands);
    }

    @Override
    public void execute(String[] params) {
        bundle.getSystem().updateBundle(BundleFactory.logoutBundle(bundle));
    }

    @Override
    public String help() {
        return  formatter.formatStringLength(75, "Loges the user out") + formatter.formatStringLength(50, "<Command>") + " | Commands: "
                + formatter.concatStringArray(getCommands());
    }
}
