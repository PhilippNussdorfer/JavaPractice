package accounts;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class Account {
    protected double balance;

    public void deposit(double amount) {
        if (amount < 0) {
            amount *= -1;
        }

        balance += amount;
    }

    public abstract double withdraw(double amount);
    public abstract boolean transfer(double amount, Long customerID);
}
