package Bank.commandInterpretor;

public class WithdrawCommand extends CommandABS {

    public WithdrawCommand(String ... commands) {
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
