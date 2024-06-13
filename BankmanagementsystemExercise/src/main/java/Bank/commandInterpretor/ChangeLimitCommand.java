package Bank.commandInterpretor;

public class ChangeLimitCommand extends CommandABS {

    public ChangeLimitCommand(String ... commands) {
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
