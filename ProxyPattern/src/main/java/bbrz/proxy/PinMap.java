package bbrz.proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PinMap {

    private Map<Account, String> accountPinMap;
    private List<Account> accountList;
    public PinMap () {

        this.accountPinMap = new HashMap<>();
        this.accountList = new ArrayList<>();
    }

    public void addPinWithAccount(Account account, String pin) {
        this.accountPinMap.put(account, pin);
        this.accountList.add(account);
    }

    public void deletePinWithAccount(Account account) {
        this.accountPinMap.remove(account);
        this.accountList.remove(account);
    }

    public Account getAccount(String user) {
        for (Account account : this.accountList) {
            if (account.getUser().equals(user)) {
                return account;
            }
        }
        return null;
    }

    public String getPin(Account account) {
        return this.accountPinMap.get(account);
    }
}
