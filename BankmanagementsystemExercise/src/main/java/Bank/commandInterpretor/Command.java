package Bank.commandInterpretor;

import Bank.Bundle;
import Bank.customExceptions.*;

public interface Command {
    boolean canHandle(String command) throws NoBundleException;
    void execute(String[] params) throws InvalidUserException, InvalidInputException, LoginFailedException, TransferFailedException, AccountTypeNotExisting, AccountTypeAlreadyExists;
    String help();
    void setBundle(Bundle bundle);
}