package Bank.commandInterpretor;

import Bank.Bundle;
import Bank.accounts.Account;
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
class WithdrawCommandTest {

    WithdrawCommand withdrawCommand = new WithdrawCommand("withdraw");

    @Mock
    Bundle bundle;
    @Mock
    Session session;
    @Mock
    Customer customer;
    @Mock
    Admin admin;
    @Mock
    Account account;

    @BeforeEach
    void setUp() {
        withdrawCommand.setBundle(bundle);
    }

    @Test
    void canHandle() throws NoBundleException {
        assertTrue(withdrawCommand.canHandle("Withdraw"));
        assertFalse(withdrawCommand.canHandle("exit"));
    }

    @Test
    void execute() throws InvalidInputException, InvalidUserException {
        Mockito.when(bundle.getSession()).thenReturn(session);
        Mockito.when(session.getUser()).thenReturn(customer);
        Mockito.when(customer.getAccount("giro")).thenReturn(account);

        withdrawCommand.execute(new String[] {"", "giro", "250"});
        Mockito.verify(account, Mockito.times(1)).withdraw(250);
    }

    @Test
    void exceptions() {
        Mockito.when(bundle.getSession()).thenReturn(session);
        Mockito.when(session.getUser()).thenReturn(customer);

        assertThrows(NumberFormatException.class, ()-> withdrawCommand.execute(new String[] {"", "giro", "250G"}));
        assertThrows(InvalidInputException.class, ()-> withdrawCommand.execute(new String[] {"", "giro"}));
        assertThrows(InvalidInputException.class, ()-> withdrawCommand.execute(new String[] {}));

        Mockito.when(session.getUser()).thenReturn(admin);

        assertThrows(InvalidUserException.class, ()-> withdrawCommand.execute(new String[] {"", "giro", "250"}));

        withdrawCommand.setBundle(null);

        assertThrows(NoBundleException.class, ()-> withdrawCommand.canHandle("withdraw"));
    }

    @Test
    void help() {
        assertEquals("Withdraws an amount of money from the selected accountType                 <Command> <Account Type> <amount>                  | Commands: withdraw", withdrawCommand.help());
    }
}