package Bank.commandInterpretor;

import Bank.customExceptions.InvalidInputException;
import Bank.customExceptions.InvalidUserException;
import Bank.customExceptions.TransferFailedException;
import Bank.person.Customer;
import Bank.person.Person;

import java.util.ArrayList;
import java.util.List;

public class TransferCommand extends CommandAbstract {

    public TransferCommand(String ... commands) {
        super(commands);
    }

    @Override
    public void execute(String[] params) throws NumberFormatException, InvalidUserException, TransferFailedException, InvalidInputException {
        List<Customer> customers = new ArrayList<>();
        var last = formatter.CheckIfElementExists(4, params);

        for (Person person : bundle.getSystem().getUser().getUsers()) {
            if (person instanceof Customer)
                customers.add((Customer) person);
        }

        if (bundle.getSession().getUser() instanceof Customer) {
            if (!((Customer) bundle.getSession().getUser()).getAccount(params[2]).transfer(Double.parseDouble(params[1]), Long.parseLong(params[3]), customers, last))
                    throw new TransferFailedException("Could not find any user with this ID or the transfer amount exceeds the limit");
        } else {
            throw new InvalidUserException("Please make sure this user is an: " + Customer.class.getSimpleName());
        }
    }

    @Override
    public String help() {
        return formatter.formatStringLength(75, "Transfers an amount of money to another account can bee used for your own accounts or someone else's")
                + formatter.formatStringLength(50, "<Amount> <Account Type> <Transfer ID> <Transfer Account Type>") + " | Commands: "
                + formatter.concatStringArray(getCommands());
    }
}
