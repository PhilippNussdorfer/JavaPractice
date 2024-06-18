package Bank.commandInterpretor;

import Bank.Bundle;
import Bank.customExceptions.InvalidInputException;
import Bank.customExceptions.LoginFailedException;
import Bank.person.*;
import Bank.tools.BundleFactory;

public class LoginCommand extends CommandAbstract {

    public LoginCommand(String ... commands) {
        super(commands);
    }

    @Override
    public void execute(String[] params) throws InvalidInputException, LoginFailedException, NumberFormatException {
        var last = formatter.CheckIfElementExists(2, params);

        for (Person user : bundle.getSystem().getUser().getUsers()) {
            Bundle tmpBundle = null;
            Admin admin = null;
            Customer customer = null;

            if (user instanceof Customer) {
                customer = (Customer) user;
                tmpBundle = BundleFactory.createCustomerBundle(new Session(user), bundle);
            }

            if (user instanceof Admin) {
                admin = (Admin) user;
                tmpBundle = BundleFactory.createAdminBundle(new Session(user), bundle);
            }

            if (user.login(last) && (
                    customer != null && customer.getCustomerID() == Long.parseLong(params[1])
                            || admin != null && admin.getEmployeeID() == Long.parseLong(params[1])
            )) {
                System.out.println("\nYou have been logged in.");
                bundle.getSystem().updateBundle(tmpBundle);
                return;
            }
        }

        throw new LoginFailedException("login failed Please make sure the inputs for the ID and pin are correct.");
    }

    @Override
    public String help() {
        return formatter.formatStringLength(75, "Login with pin and ID") + formatter.formatStringLength(50, "<Command> <ID> <Pin>") + " | Commands: "
                + formatter.concatStringArray(getCommands());
    }
}
