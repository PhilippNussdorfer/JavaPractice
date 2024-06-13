package Bank.accounts;

import Bank.person.Customer;

import java.util.List;
import java.util.Objects;

public class CreditAccount extends Account {
    public CreditAccount(double balance, AccountType accountType) {
        super(balance, accountType);
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0)
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
                    balance -= amount;
                    customerAccount.balance += amount;
                    return true;
                }
                return false;
            }
        }

        return false;
    }
}
