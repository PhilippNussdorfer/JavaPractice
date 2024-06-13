package Bank.tools;

import Bank.Bundle;
import Bank.commandInterpretor.*;
import Bank.person.Session;
import Bank.person.User;

public class BundleFactory {

    public static Bundle createCustomerBundle(Session session) {
        var bundle = new Bundle(session);

        var interpreter = new Interpreter();
        interpreter.addCommand(
                new ChangeLimitCommand(bundle, "changeLimit", "chL"),
                new LogoutCommand(bundle, "logout"),
                new DepositCommand(bundle, "deposit", "depo"),
                new TransferCommand(bundle, "transfer"),
                new WithdrawCommand(bundle, "withdraw"),
                new HelpCommand(bundle, "help"),
                new ExitCommand(bundle, "exit")
        );

        bundle.addInterpreter(interpreter);

        return bundle;
    }

    public static Bundle createAdminBundle(Session session) {
        var bundle = new Bundle(session);

        var interpreter = new Interpreter();
        interpreter.addCommand(
                new LogoutCommand(bundle, "logout"),
                new AddCustomerCommand(bundle, "add", "addCustomer"),
                new HelpCommand(bundle, "help"),
                new ExitCommand(bundle, "exit")
        );

        bundle.addInterpreter(interpreter);

        return bundle;
    }

    public static Bundle startingBundle(User user) {
        var bundle = new Bundle(null);

        var interpreter = new Interpreter();
        interpreter.addCommand(
                new LoginCommand(bundle, user, "login"),
                new HelpCommand(bundle, "help"),
                new ExitCommand(bundle, "exit")
        );

        bundle.addInterpreter(interpreter);

        return bundle;
    }
}
