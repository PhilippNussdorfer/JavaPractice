package bbrz.proxy;

import java.util.List;

public class Account implements AccountInterface {

    private double balance;
    private String User;

    public Account(double balance, String user) {
        this.balance = balance;
        this.User = user;
    }

    public String getUser() {
        return this.User;
    }

    @Override
    public void deposit(double currency) {
        this.balance += currency;
    }

    @Override
    public double draw(double currency) {
        this.balance -= currency;
        return currency;
    }

    @Override
    public double balance() {
        return this.balance;
    }
}
