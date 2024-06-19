package Bank.customExceptions;

public class AccountTypeAlreadyExists extends Exception {
    public AccountTypeAlreadyExists(String msg) {
        super(msg);
    }
}
