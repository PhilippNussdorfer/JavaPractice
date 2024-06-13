package Bank.commandInterpretor;

import Bank.accounts.AccountType;
import Bank.accounts.GiroAccount;
import Bank.person.Customer;

public class ChangeLimitCommand extends CommandAbstract {

    public ChangeLimitCommand(String ... commands) {
        super(commands);
    }

    @Override
    public void execute(String[] params) throws NumberFormatException {
        if (bundle.getSession().getUser() instanceof Customer) {
            ((GiroAccount) ((Customer) bundle.getSession().getUser()).getAccount(AccountType.GIRO.getValue())).setLimit(Double.parseDouble(params[1]));
        } else {
            System.out.println("Please make sure this user is an: " + Customer.class.getSimpleName());
        }
    }

    @Override
    public String help() {
        return formatter.formatStringLength(75, "Changes the limit of your giro account.") + formatter.formatStringLength(50, "<Command> <limit>") + " | Commands: "
                + formatter.concatStringArray(getCommands());
    }
}
