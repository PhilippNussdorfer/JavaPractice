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
                new ChangeLimitCommand("changeLimit", "chL"),
                new LogoutCommand("logout"),
                new DepositCommand("deposit", "depo"),
                new TransferCommand("transfer"),
                new WithdrawCommand("withdraw"),
                new HelpCommand("help"),
                new ExitCommand("exit")
        );

        bundle.addInterpreter(interpreter);
        bundle.getInterpreter().addBundle(bundle);

        return bundle;
    }

    public static Bundle createAdminBundle(Session session) {
        var bundle = new Bundle(session);

        var interpreter = new Interpreter();
        interpreter.addCommand(
                new LogoutCommand("logout"),
                new AddCustomerCommand("add", "addCustomer"),
                new HelpCommand("help"),
                new ExitCommand("exit")
        );

        bundle.addInterpreter(interpreter);
        bundle.getInterpreter().addBundle(bundle);

        return bundle;
    }

    public static Bundle startingBundle(User user) {
        var bundle = new Bundle(null);

        var interpreter = new Interpreter();
        interpreter.addCommand(
                new LoginCommand(user, "login"),
                new HelpCommand("help"),
                new ExitCommand("exit")
        );

        bundle.addInterpreter(interpreter);
        bundle.getInterpreter().addBundle(bundle);

        return bundle;
    }
}
