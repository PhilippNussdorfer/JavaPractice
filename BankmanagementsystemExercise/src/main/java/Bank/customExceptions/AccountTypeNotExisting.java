package Bank.customExceptions;

public class AccountTypeNotExisting extends Exception {
    public AccountTypeNotExisting(String msg) {
        super(msg);
    }
}
