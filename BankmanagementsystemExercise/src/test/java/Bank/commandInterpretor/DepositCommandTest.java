package Bank.commandInterpretor;

import Bank.Bundle;
import Bank.accounts.Account;
import Bank.customExceptions.AccountTypeNotExisting;
import Bank.customExceptions.InvalidInputException;
import Bank.customExceptions.InvalidUserException;
import Bank.customExceptions.NoBundleException;
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
class DepositCommandTest {

    DepositCommand depositCommand = new DepositCommand("deposit");

    @Mock
    Bundle bundle;
    @Mock
    Customer customer;
    @Mock
    Admin admin;
    @Mock
    Session session;
    @Mock
    Account account;

    @BeforeEach
    void setUp() {
        depositCommand.setBundle(bundle);
    }

    @Test
    void canHandle() throws NoBundleException {
        assertTrue(depositCommand.canHandle("Deposit"));
        assertFalse(depositCommand.canHandle("exit"));
    }

    @Test
    void execute() throws InvalidUserException, InvalidInputException, AccountTypeNotExisting {
        Mockito.when(bundle.getSession()).thenReturn(session);
        Mockito.when(session.getUser()).thenReturn(customer);
        Mockito.when(customer.getAccount(Mockito.anyString())).thenReturn(account);

        depositCommand.execute(new String[] {"", "", "22"});
        Mockito.verify(account, Mockito.times(1)).deposit(22);
    }

    @Test
    void help() {
        assertEquals("Deposits an amount of money on a specific account. Account types are: giro, savings, credit <Command> <Account> <Amount>                      | Commands: deposit", depositCommand.help());
    }

    @Test
    void exceptions() {
        Mockito.when(bundle.getSession()).thenReturn(session);
        Mockito.when(session.getUser()).thenReturn(admin);

        assertThrows(InvalidUserException.class, ()-> depositCommand.execute(new String[] {"", "", "22"}));

        Mockito.when(session.getUser()).thenReturn(customer);

        assertThrows(NumberFormatException.class, ()-> depositCommand.execute(new String[] {"", "", "22a"}));
        assertThrows(InvalidInputException.class, ()-> depositCommand.execute(new String[] {"", ""}));
    }
}