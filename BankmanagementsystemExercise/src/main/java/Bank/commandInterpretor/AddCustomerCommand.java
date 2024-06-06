package Bank.commandInterpretor;

import Bank.Bundle;
import Bank.person.Admin;
import Bank.person.Customer;

public class AddCustomerCommand extends CommandABS {

    public AddCustomerCommand(String[] commands, Bundle bundle) {
        super(commands, bundle);
    }

    @Override
    public void execute(String[] params) {
        try {
            if (bundle.getUser() instanceof Admin && params.length >= 6) {
                bundle.getCustomers().add(new Customer(params[1], params[2], Long.getLong(params[3]), Long.getLong(params[4]), params[5]));
            }
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String help() {
        return formatter.formatStringLength(75, "Adds new customer " + formatter.formatStringLength(50, "<Command> <Name> <Birth Day> <Social Number> <ID> <pin>") + " | Commands: "
                + formatter.concatStringArray(getCommands()));
    }
}
