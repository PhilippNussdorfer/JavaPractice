package Bank.commandInterpretor;

import Bank.accounts.AccountType;
import Bank.person.Customer;

public class DepositCommand extends CommandAbstract {

    public DepositCommand(String ... commands) {
        super(commands);
    }

    @Override
    public void execute(String[] params) throws NumberFormatException {
        if (bundle.getSession().getUser() instanceof Customer) {
            ((Customer) bundle.getSession().getUser()).getAccount(params[1]).deposit(Double.parseDouble(params[2]));
        } else {
            System.out.println("Please make sure this user is an " + Customer.class.getSimpleName());
        }
    }

    @Override
    public String help() {
        String[] arr = {AccountType.GIRO.getValue(), AccountType.SAVINGS.getValue(), AccountType.CREDIT.getValue()};

        return formatter.formatStringLength(75, "Deposits an amount of money on a specific account. Account types are: " + formatter.concatStringArray(arr))
                + formatter.formatStringLength(50, "<Command> <Account> <Amount>") + " | Commands: "
                + formatter.concatStringArray(getCommands());
    }
}
