package Bank.commandInterpretor;

import Bank.person.Customer;

public class WithdrawCommand extends CommandAbstract {

    public WithdrawCommand(String ... commands) {
        super(commands);
    }

    @Override
    public void execute(String[] params) throws NumberFormatException {
        if (bundle.getSession().getUser() instanceof Customer) {
            ((Customer) bundle.getSession().getUser()).getAccount(params[1]).withdraw(Double.parseDouble(params[2]));
        } else {
            System.out.println("Please make sure this user is an: " + Customer.class.getSimpleName());
        }
    }

    @Override
    public String help() {
        return formatter.formatStringLength(75, "Withdraws an amount of money from the selected accountType")
                + formatter.formatStringLength(50, "<Command> <Account Type> <amount>") + " | Commands: "
                + formatter.concatStringArray(getCommands());
    }
}
