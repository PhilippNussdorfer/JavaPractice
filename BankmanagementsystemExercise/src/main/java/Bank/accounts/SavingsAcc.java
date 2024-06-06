package Bank.accounts;

import Bank.person.Customer;

import java.util.List;
import java.util.Objects;

public class SavingsAcc extends Account {
    public SavingsAcc(double balance, AccountTypes accountType) {
        super(balance, accountType);
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0 || balance - amount < 0)
            return false;

        balance -= amount;
        return true;
    }

    @Override
    public boolean transfer(double amount, Long customerID, List<Customer> customers, String accountType) {
        if (amount <= 0)
            return false;

        for (Customer customer : customers) {
            if (Objects.equals(customer.getCustomerID(), customerID)) {
                Account customerAccount = customer.getAccount(accountType);

                if (customerAccount != null) {
                    if (balance - amount >= 0) {
                        balance -= amount;
                        customerAccount.balance += amount;
                        return true;
                    }
                }
                return false;
            }
        }

        return false;
    }
}
