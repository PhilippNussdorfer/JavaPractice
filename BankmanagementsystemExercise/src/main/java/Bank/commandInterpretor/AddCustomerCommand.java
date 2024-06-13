package Bank.commandInterpretor;

import Bank.person.Customer;

public class AddCustomerCommand extends CommandAbstract {

    public AddCustomerCommand(String ... commands) {
        super(commands);
    }

    @Override
    public void execute(String[] params) throws NumberFormatException {
        bundle.getSystem().getUser().addUser(new Customer(params[1], params[2], Long.parseLong(params[3]), Long.parseLong(params[4]), params[5]));
        System.out.println("Added new user: " + params[1]);
    }

    @Override
    public String help() {
        return formatter.formatStringLength(75, "Adds new customer " + formatter.formatStringLength(50, "<Command> <Name> <Birth Day> <Social Number> <ID> <pin>") + " | Commands: "
                + formatter.concatStringArray(getCommands()));
    }
}
