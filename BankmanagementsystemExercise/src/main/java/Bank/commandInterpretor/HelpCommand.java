package Bank.commandInterpretor;

public class HelpCommand extends CommandABS {

    public HelpCommand(String ... commands) {
        super(commands);
    }

    @Override
    public void execute(String[] params) {
        System.out.println("=".repeat(250));
        for (Command command : bundle.getInterpreter().getCommands()) {
            System.out.println(command.help());
        }
        System.out.println("=".repeat(250));
    }

    @Override
    public String help() {
        return formatter.formatStringLength(75, "Help command shows all possible commands") + formatter.formatStringLength(50, "<Command>") + " | Commands: "
                + formatter.concatStringArray(getCommands());
    }
}
