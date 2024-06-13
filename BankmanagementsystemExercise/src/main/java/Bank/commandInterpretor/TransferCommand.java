package Bank.commandInterpretor;

public class TransferCommand extends CommandABS {

    public TransferCommand(String ... commands) {
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
