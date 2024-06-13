package Bank.commandInterpretor;

public class DepositCommand extends CommandABS {

    public DepositCommand(String ... commands) {
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
