package Bank.commandInterpretor;

import Bank.BankManagementSystem;
import Bank.person.*;
import Bank.tools.BundleFactory;

public class LoginCommand extends CommandABS {
    private final User user;

    public LoginCommand(User user, String ... commands) {
        super(commands);

        this.user = user;
    }

    @Override
    public void execute(String[] params) {
        try {
            for (Person user : this.user.getUsers()) {
                if (user.login(params[2])) {
                    System.out.println("\nYou have been logged in.");

                    if (user instanceof Customer)
                        BankManagementSystem.updateBundle(BundleFactory.createCustomerBundle(new Session(user)));
                    if (user instanceof Admin)
                        BankManagementSystem.updateBundle(BundleFactory.createAdminBundle(new Session(user)));

                } else {
                    System.out.println("login failed Please make sure the inputs for the ID and pin are correct.");
                }
            }

        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String help() {
        return formatter.formatStringLength(75, "Login with pin and ID") + formatter.formatStringLength(50, "<Command> <ID> <Pin>") + " | Commands: "
                + formatter.concatStringArray(getCommands());
    }
}
