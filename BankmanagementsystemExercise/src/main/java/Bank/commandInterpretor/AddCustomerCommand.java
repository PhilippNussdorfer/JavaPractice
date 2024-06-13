package Bank.commandInterpretor;

public class AddCustomerCommand extends CommandABS {

    public AddCustomerCommand(String ... commands) {
        super(commands);
    }

    @Override
    public void execute(String[] params) {
        try {

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
