package Bank.accounts;

import lombok.Getter;
import lombok.Setter;
import Bank.person.Customer;

import java.util.List;
import java.util.Objects;

@Getter
public class GiroAccount extends Account {
    @Setter
    private double limit;

    public GiroAccount(double balance, AccountType accountType, double limit) {
        super(balance, accountType);

        this.limit = limit;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0)
            return false;

        if (balance - amount < this.limit)
            return false;
        else {
            balance -= amount;
            return true;
        }
    }

    @Override
    public boolean transfer(double amount, Long customerID, List<Customer> customers, String accountType) {
        if (amount <= 0)
            return false;

        for (Customer customer : customers) {
            if (Objects.equals(customer.getCustomerID(), customerID)) {
                Account customerAccount = customer.getAccount(accountType);

                if (customerAccount != null) {
                    if (balance - amount >= this.limit) {
                        balance -= amount;
                        customerAccount.deposit(amount);
                        return true;
                    }
                }
                return false;
            }
        }

        return false;
    }
}
