package person;

import accounts.*;
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
        if (accountType.equalsIgnoreCase(AccountTypes.GIRO.getValue()))
            accounts.add(new GiroAcc(50, AccountTypes.GIRO, 150));

        if (accountType.equalsIgnoreCase(AccountTypes.SAVINGS.getValue()))
            accounts.add(new SavingsAcc(75, AccountTypes.SAVINGS));

        if (accountType.equalsIgnoreCase(AccountTypes.CREDIT.getValue()))
            accounts.add(new CreditAcc(35, AccountTypes.CREDIT));
    }

    public Account getAccount(String accountType) {
        for (Account account : accounts) {
            if (account.getAccountType().getValue().equalsIgnoreCase(accountType))
                return account;
        }
        return null;
    }
}
