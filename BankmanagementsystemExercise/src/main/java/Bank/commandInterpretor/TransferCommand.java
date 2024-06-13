package Bank.commandInterpretor;

import Bank.BankManagementSystem;
import Bank.person.Customer;
import Bank.person.Person;

import java.util.ArrayList;
import java.util.List;

public class TransferCommand extends CommandAbstract {

    public TransferCommand(String ... commands) {
        super(commands);
    }

    @Override
    public void execute(String[] params) throws NumberFormatException {
        List<Customer> customers = new ArrayList<>();

        for (Person person : bundle.getSystem().getUser().getUsers()) {
            if (person instanceof Customer)
                customers.add((Customer) person);
        }

        if (bundle.getSession().getUser() instanceof Customer) {
            ((Customer) bundle.getSession().getUser()).getAccount(params[2]).transfer(Double.parseDouble(params[1]), Long.parseLong(params[3]), customers, params[4]);
        } else {
            System.out.println("Please make sure this user is an: " + Customer.class.getSimpleName());
        }
    }

    @Override
    public String help() {
        return formatter.formatStringLength(75, "Transfers an amount of money to another account can bee used for your own accounts or someone else's")
                + formatter.formatStringLength(50, "<Amount> <Account Type> <Transfer ID> <Transfer Account Type>") + " | Commands: "
                + formatter.concatStringArray(getCommands());
    }
}
