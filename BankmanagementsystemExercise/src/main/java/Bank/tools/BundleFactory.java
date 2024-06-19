package Bank.tools;

import Bank.BankManagementSystem;
import Bank.Bundle;
import Bank.commandInterpretor.*;
import Bank.person.Session;

public class BundleFactory {

    private static final StringFormatter formatter = new StringFormatter();

    public static Bundle createCustomerBundle(Session session, Bundle oldBundle) {
        var bundle = new Bundle(session, oldBundle);

        var interpreter = new Interpreter(formatter);
        interpreter.addCommand(
                new ChangeLimitCommand("changeLimit", "chL"),
                new LogoutCommand("logout"),
                new DepositCommand("deposit", "depo"),
                new TransferCommand("transfer"),
                new WithdrawCommand("withdraw"),
                new HelpCommand("help"),
                new ExitCommand("exit")
        );

        bundle.addInterpreter(interpreter);
        bundle.getInterpreter().setBundleForCommands(bundle);

        return bundle;
    }

    public static Bundle createAdminBundle(Session session, Bundle oldBundle) {
        var bundle = new Bundle(session, oldBundle);

        var interpreter = new Interpreter(formatter);
        interpreter.addCommand(
                new LogoutCommand("logout"),
                new AddCustomerCommand("add", "addCustomer"),
                new HelpCommand("help"),
                new ExitCommand("exit")
        );

        bundle.addInterpreter(interpreter);
        bundle.getInterpreter().setBundleForCommands(bundle);

        return bundle;
    }

    public static Bundle logoutBundle(Bundle oldBundle) {
        var bundle = new Bundle(null, oldBundle);

        var interpreter = new Interpreter(formatter);
        interpreter.addCommand(
                new LoginCommand("login"),
                new HelpCommand("help"),
                new ExitCommand("exit")
        );

        bundle.addInterpreter(interpreter);
        bundle.getInterpreter().setBundleForCommands(bundle);

        return bundle;
    }

    public static Bundle startingBundle(BankManagementSystem system) {
        var bundle = new Bundle(null, system);

        var interpreter = new Interpreter(formatter);
        interpreter.addCommand(
                new LoginCommand("login"),
                new HelpCommand("help"),
                new ExitCommand("exit")
        );

        bundle.addInterpreter(interpreter);
        bundle.getInterpreter().setBundleForCommands(bundle);

        return bundle;
    }
}
