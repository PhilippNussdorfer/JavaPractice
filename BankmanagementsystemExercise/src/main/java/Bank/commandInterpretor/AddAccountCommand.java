package Bank.commandInterpretor;

import Bank.accounts.AccountType;
import Bank.customExceptions.*;
import Bank.person.Customer;

public class AddAccountCommand extends CommandAbstract {

    public AddAccountCommand(String ... commands) {
        super(commands);
    }

    @Override
    public void execute(String[] params) throws InvalidUserException, InvalidInputException, LoginFailedException, TransferFailedException, AccountTypeNotExisting, AccountTypeAlreadyExists {
        var user = bundle.getSession().getUser();
        var last = formatter.CheckIfElementExists(1, params);

        if (user instanceof Customer) {
            ((Customer) user).addAccount(last);
        } else {
            throw new InvalidUserException("This user is not an " + Customer.class.getSimpleName());
        }
    }

    @Override
    public String help() {
        return formatter.formatStringLength(75, "Adds accounts that are not existing") + formatter.formatStringLength(50, "<Command> <Account type>")
                + " | Account types: " + formatter.concatStringArray(AccountType.GIRO.getValue(), AccountType.CREDIT.getValue(), AccountType.SAVINGS.getValue())
                + " | Commands: " + formatter.concatStringArray(getCommands());
    }
}
