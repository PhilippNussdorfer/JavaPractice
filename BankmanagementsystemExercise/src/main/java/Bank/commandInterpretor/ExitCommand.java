package Bank.commandInterpretor;

public class ExitCommand extends CommandABS {

    public ExitCommand(String ... commands) {
        super(commands);
    }

    @Override
    public void execute(String[] params) {

    }

    @Override
    public String help() {
        return null;
    }
}
