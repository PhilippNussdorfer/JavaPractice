package Bank.commandInterpretor;

import Bank.Bundle;
import Bank.accounts.Account;
import Bank.customExceptions.*;
import Bank.person.Admin;
import Bank.person.Customer;
import Bank.person.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AddAccountCommandTest {

    AddAccountCommand addAccountCommand = new AddAccountCommand("add");
    @Mock
    Bundle bundle;
    @Mock
    Session session;
    @Mock
    Admin admin;
    @Mock
    Customer customer;

    @BeforeEach
    void setUp() {
        addAccountCommand.setBundle(bundle);
    }

    @Test
    void execute() throws AccountTypeAlreadyExists, AccountTypeNotExisting, InvalidInputException, LoginFailedException, InvalidUserException, TransferFailedException {
        Mockito.when(bundle.getSession()).thenReturn(session);
        Mockito.when(session.getUser()).thenReturn(customer);

        addAccountCommand.execute(new String[] {"add", "giro"});
        Mockito.verify(customer, Mockito.times(1)).addAccount("giro");
    }

    @Test
    void canHandle() throws NoBundleException {
        assertTrue(addAccountCommand.canHandle("ADD"));
        assertFalse(addAccountCommand.canHandle("exit"));
    }

    @Test
    void exceptions() throws AccountTypeAlreadyExists, AccountTypeNotExisting {
        Mockito.when(bundle.getSession()).thenReturn(session);
        Mockito.when(session.getUser()).thenReturn(customer);
        Mockito.doThrow(AccountTypeAlreadyExists.class).when(customer).addAccount("giro");
        assertThrows(AccountTypeAlreadyExists.class, ()-> addAccountCommand.execute(new String[] {"add", "giro"}));

        Mockito.doThrow(AccountTypeNotExisting.class).when(customer).addAccount("garo");
        assertThrows(AccountTypeNotExisting.class, ()-> addAccountCommand.execute(new String[] {"add", "garo"}));

        Mockito.when(session.getUser()).thenReturn(admin);
        assertThrows(InvalidUserException.class, ()-> addAccountCommand.execute(new String[] {"add", "giro"}));

        addAccountCommand.setBundle(null);
        assertThrows(NoBundleException.class, ()-> addAccountCommand.canHandle("add"));
    }

    @Test
    void help() {
        assertEquals("Adds accounts that are not existing                                        <Command> <Account type>                           | Account types: giro, credit, savings | Commands: add", addAccountCommand.help());
    }
}