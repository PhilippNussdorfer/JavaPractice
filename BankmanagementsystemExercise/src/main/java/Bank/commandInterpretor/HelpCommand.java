package Bank.commandInterpretor;

import Bank.Bundle;

public class HelpCommand extends CommandABS {

    public HelpCommand(String[] commands, Bundle bundle) {
        super(commands, bundle);
    }

    @Override
    public void execute(String[] params) {
        for (Command command : bundle.getInterpreter().getCommands()) {
            command.help();
        }
    }

    @Override
    public String help() {
        return formatter.formatStringLength(75, "Help command shows all possible commands") + formatter.formatStringLength(50, "<Command>") + " | Commands: "
                + formatter.concatStringArray(getCommands());
    }
}
