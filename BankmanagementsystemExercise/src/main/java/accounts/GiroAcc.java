package accounts;

import lombok.Getter;
import person.Customer;

import java.util.List;
import java.util.Objects;

@Getter
public class GiroAcc extends Account {
    private final double limit;

    public GiroAcc(double balance, AccountTypes accountType, double limit) {
        super(balance, accountType);

        if (limit < 0)
            this.limit = limit;
        else
            this.limit = (limit * -1);
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
