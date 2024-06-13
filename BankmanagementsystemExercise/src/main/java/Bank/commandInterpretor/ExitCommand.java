package Bank.commandInterpretor;

public class ExitCommand extends CommandAbstract {

    public ExitCommand(String ... commands) {
        super(commands);
    }

    @Override
    public void execute(String[] params) {
        bundle.setRunning(false);
        System.out.println("Have a nice day!");
    }

    @Override
    public String help() {
        return formatter.formatStringLength(75, "Exits the program") + formatter.formatStringLength(50, "<Command>") + " | Commands: "
                + formatter.concatStringArray(getCommands());
    }
}
