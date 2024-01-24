package bbrz.proxy;

import java.util.Scanner;

public class Proxy implements AccountInterface {

    private Account account;
    private PinMap pinMap;

    public Proxy(Account account, PinMap pinMap) {
        this.account = account;
        this.pinMap = pinMap;
    }

    public Account getAccount() {
        return this.account;
    }

    private String dialog() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter pin:");
        System.out.print(">");
        return scan.nextLine();
    }

    @Override
    public void deposit(double currency) {
        String pin = dialog();
        if (this.account != null && this.pinMap.getPin(this.account).equals(pin)) {
            this.account.deposit(currency);
        } else {
            System.out.println("Wrong Pin!");
        }
    }

    @Override
    public double draw(double currency) {
        String pin = dialog();
        if (this.account != null && this.pinMap.getPin(this.account).equals(pin)) {
            return this.account.draw(currency);
        } else {
            System.out.println("Wrong Pin!");
            return 0;
        }
    }

    @Override
    public double balance() {
        if (this.account != null) {
            return this.account.balance();
        } else {
            return 0;
        }
    }
}
