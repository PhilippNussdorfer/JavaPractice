package Bank.accounts;

import Bank.customExceptions.AccountTypeNotExisting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import Bank.person.Customer;

import java.util.List;

@AllArgsConstructor
public abstract class Account {
    protected double balance;
    @Getter
    protected AccountType accountType;

    public void deposit(double amount) {
        if (amount < 0) {
            amount *= -1;
        }

        balance += amount;
    }

    public abstract boolean withdraw(double amount);
    public abstract boolean transfer(double amount, Long customerID, List<Customer> customers, String accountType) throws AccountTypeNotExisting;
}
