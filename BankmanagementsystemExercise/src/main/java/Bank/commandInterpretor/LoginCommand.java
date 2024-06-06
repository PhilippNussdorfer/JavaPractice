package Bank.commandInterpretor;

import Bank.Bundle;
import Bank.person.Admin;
import Bank.person.Customer;

import java.util.Objects;

public class LoginCommand extends CommandABS {

    public LoginCommand(String[] commands, Bundle bundle) {
        super(commands, bundle);
    }

    @Override
    public void execute(String[] params) {
        try {
            for (Customer customer : bundle.getCustomers()) {
                var id = Long.getLong(params[1]);

                if (Objects.equals(id, customer.getCustomerID())) {
                    if (customer.login(params[2])) {
                        bundle.setUser(customer);
                    }
                }
            }

            for (Admin admin : bundle.getAdmins()) {
                var id = Long.getLong(params[1]);

                if (Objects.equals(id, admin.getEmployeeID())) {
                    if (admin.login(params[2])) {
                        bundle.setUser(admin);
                    }
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
