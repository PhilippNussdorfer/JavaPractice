package Bank.commandInterpretor;

import Bank.Bundle;
import Bank.accounts.GiroAccount;
import Bank.customExceptions.AccountTypeNotExisting;
import Bank.customExceptions.InvalidInputException;
import Bank.customExceptions.InvalidUserException;
import Bank.customExceptions.NoBundleException;
import Bank.person.Admin;
import Bank.person.Customer;
import Bank.person.Session;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ChangeLimitCommandTest {

    ChangeLimitCommand changeLimitCommand = new ChangeLimitCommand("change");

    @Mock
    Bundle bundle;
    @Mock
    Session session;
    @Mock
    Admin admin;
    @Mock
    Customer customer;
    @Mock
    GiroAccount giro;


    @Test
    void canHandle() throws NoBundleException {
        assertThrows(NoBundleException.class, ()-> changeLimitCommand.canHandle(""));

        changeLimitCommand.setBundle(bundle);
        assertTrue(changeLimitCommand.canHandle("Change"));
        assertFalse(changeLimitCommand.canHandle("addCustomer"));
    }

    @Test
    void changeLimitCommand() throws InvalidUserException, InvalidInputException, AccountTypeNotExisting {
        Mockito.when(bundle.getSession()).thenReturn(session);
        Mockito.when(session.getUser()).thenReturn(customer);
        Mockito.when(customer.getAccount(Mockito.anyString())).thenReturn(giro);

        changeLimitCommand.setBundle(bundle);
        changeLimitCommand.execute(new String[] {"", "135.87"});
        Mockito.verify(giro, Mockito.times(1)).setLimit(135.87);
    }

    @Test
    void exceptionsOnChangeLimitCommand() {
        Mockito.when(bundle.getSession()).thenReturn(session);
        Mockito.when(session.getUser()).thenReturn(admin);

        changeLimitCommand.setBundle(bundle);
        assertThrows(InvalidUserException.class, ()-> changeLimitCommand.execute(new String[] {"", "2"}));
        assertThrows(InvalidInputException.class, ()-> changeLimitCommand.execute(new String[] {}));

        Mockito.when(session.getUser()).thenReturn(customer);

        assertThrows(NumberFormatException.class, ()-> changeLimitCommand.execute(new String[] {"", "a"}));
    }

    @Test
    void changeLimitCommandHelp() {
        assertEquals("Changes the limit of your giro account.                                    <Command> <limit>                                  | Commands: change",
                changeLimitCommand.help());
    }
}