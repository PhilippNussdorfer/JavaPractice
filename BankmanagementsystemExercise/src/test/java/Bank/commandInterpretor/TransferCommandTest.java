package Bank.commandInterpretor;

import Bank.BankManagementSystem;
import Bank.Bundle;
import Bank.accounts.Account;
import Bank.accounts.AccountType;
import Bank.customExceptions.InvalidInputException;
import Bank.customExceptions.InvalidUserException;
import Bank.customExceptions.NoBundleException;
import Bank.customExceptions.TransferFailedException;
import Bank.person.Admin;
import Bank.person.Customer;
import Bank.person.Session;
import Bank.person.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransferCommandTest {

    TransferCommand transferCommand = new TransferCommand("transfer");

    @Mock
    Bundle bundle;
    @Mock
    BankManagementSystem system;
    @Mock
    User user;
    @Mock
    Customer customer_1;
    @Mock
    Customer customer_2;
    @Mock
    Admin admin;
    @Mock
    Session session;
    @Mock
    Account account;

    @BeforeEach
    void setUp() {
        transferCommand.setBundle(bundle);
    }

    @Test
    void canHandle() throws NoBundleException {
        assertTrue(transferCommand.canHandle("Transfer"));
        assertFalse(transferCommand.canHandle("exit"));
    }

    @Test
    void execute() throws InvalidInputException, InvalidUserException, TransferFailedException {
        Mockito.when(bundle.getSession()).thenReturn(session);
        Mockito.when(bundle.getSystem()).thenReturn(system);
        Mockito.when(system.getUser()).thenReturn(user);
        Mockito.when(user.getUsers()).thenReturn(List.of(admin, customer_2, customer_1));
        Mockito.when(session.getUser()).thenReturn(customer_2);
        Mockito.when(customer_2.getAccount("giro")).thenReturn(account);
        Mockito.when(account.transfer(Mockito.anyDouble(), Mockito.anyLong(), Mockito.anyList(), Mockito.anyString())).thenReturn(true);

        transferCommand.execute(new String[] {"transfer", "1250", "giro", "11", "giro"});
        Mockito.verify(account, Mockito.times(1)).transfer(1250D, 11L, List.of(customer_2, customer_1), "giro");
    }

    @Test
    void exception() {
        Mockito.when(bundle.getSession()).thenReturn(session);
        Mockito.when(bundle.getSystem()).thenReturn(system);
        Mockito.when(system.getUser()).thenReturn(user);
        Mockito.when(user.getUsers()).thenReturn(List.of(admin, customer_2, customer_1));
        Mockito.when(session.getUser()).thenReturn(admin);

        assertThrows(InvalidUserException.class, ()-> transferCommand.execute(new String[] {"transfer", "1250", "giro", "11", "giro"}));
        assertThrows(InvalidInputException.class, ()-> transferCommand.execute(new String[] {"transfer"}));
        assertThrows(InvalidInputException.class, ()-> transferCommand.execute(new String[] {}));

        Mockito.when(session.getUser()).thenReturn(customer_2);
        Mockito.when(customer_2.getAccount("giro")).thenReturn(account);
        Mockito.when(account.transfer(Mockito.anyDouble(), Mockito.anyLong(), Mockito.anyList(), Mockito.anyString())).thenReturn(false);

        assertThrows(TransferFailedException.class, ()-> transferCommand.execute(new String[] {"transfer", "1250", "giro", "11", "giro"}));

        transferCommand.setBundle(null);

        assertThrows(NoBundleException.class, ()-> transferCommand.canHandle(""));
    }

    @Test
    void help() {
        assertEquals("Transfers an amount of money to another account can bee used for your own accounts or someone else's<Amount> <Account Type> <Transfer ID> <Transfer Account Type> | Commands: transfer", transferCommand.help());
    }
}