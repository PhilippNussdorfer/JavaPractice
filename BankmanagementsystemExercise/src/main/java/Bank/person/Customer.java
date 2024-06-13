package Bank.person;

import Bank.accounts.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Customer extends Person {
    @Getter
    private final Long customerID;
    @Getter
    private final List<Account> accounts = new ArrayList<>();

    public Customer(String name, String birthDay, Long socialNumber, Long customerID, String pin) {
        super(name, birthDay, socialNumber, pin);

        this.customerID = customerID;
    }

    public void addAccount(String accountType) {
        if (accountType.equalsIgnoreCase(AccountType.GIRO.getValue()))
            accounts.add(new GiroAccount(50, AccountType.GIRO, 150));

        if (accountType.equalsIgnoreCase(AccountType.SAVINGS.getValue()))
            accounts.add(new SavingsAccount(75, AccountType.SAVINGS));

        if (accountType.equalsIgnoreCase(AccountType.CREDIT.getValue()))
            accounts.add(new CreditAccount(35, AccountType.CREDIT));
    }

    public Account getAccount(String accountType) {
        for (Account account : accounts) {
            if (account.getAccountType().getValue().equalsIgnoreCase(accountType))
                return account;
        }
        return null;
    }
}
