package Bank.commandInterpretor;

import Bank.person.Customer;

public class AddCustomerCommand extends CommandAbstract {

    public AddCustomerCommand(String ... commands) {
        super(commands);
    }

    @Override
    public void execute(String[] params) {
        try {
            bundle.getSystem().getUser().addUser(new Customer(params[1], params[2], Long.getLong(params[3]), Long.getLong(params[4]), params[5]));
            System.out.println("Added new user: " + params[1]);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage() + "\n Please make sure that the social number and the ID are numbers.");
        }
    }

    @Override
    public String help() {
        return formatter.formatStringLength(75, "Adds new customer " + formatter.formatStringLength(50, "<Command> <Name> <Birth Day> <Social Number> <ID> <pin>") + " | Commands: "
                + formatter.concatStringArray(getCommands()));
    }
}
