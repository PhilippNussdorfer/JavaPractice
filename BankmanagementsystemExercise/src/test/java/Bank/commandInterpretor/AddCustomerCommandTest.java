package Bank.commandInterpretor;

import Bank.BankManagementSystem;
import Bank.Bundle;
import Bank.customExceptions.InvalidInputException;
import Bank.customExceptions.InvalidUserException;
import Bank.customExceptions.NoBundleException;
import Bank.person.Admin;
import Bank.person.Customer;
import Bank.person.Session;
import Bank.person.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AddCustomerCommandTest {
    AddCustomerCommand addCustomerCommand = new AddCustomerCommand("add");

    @Mock
    Bundle bundle;
    @Mock
    BankManagementSystem system;
    @Mock
    User user;
    @Mock
    Admin admin;
    @Mock
    Customer customer;
    @Mock
    Session session;

    @Test
    void canHandle() throws NoBundleException {
        assertThrows(NoBundleException.class, ()-> addCustomerCommand.canHandle("add"));

        addCustomerCommand.setBundle(bundle);
        assertTrue(addCustomerCommand.canHandle("add"));
        assertFalse(addCustomerCommand.canHandle("addCustomer"));
    }

    @Test
    void AddCommand() throws InvalidInputException, InvalidUserException {
        addCustomerCommand.setBundle(bundle);

        Mockito.when(bundle.getSystem()).thenReturn(system);
        Mockito.when(system.getUser()).thenReturn(user);
        Mockito.when(bundle.getSession()).thenReturn(session);
        Mockito.when(session.getUser()).thenReturn(admin);

        addCustomerCommand.execute(new String[] {"", "Hans", "2-3-1999", "1234", "5665", "112A"});
        Mockito.verify(user, Mockito.times(1)).addUser(Mockito.any(Customer.class));
    }

    @Test
    void ExceptionOnAddCommand() {
        addCustomerCommand.setBundle(bundle);

        Mockito.when(bundle.getSystem()).thenReturn(system);
        Mockito.when(system.getUser()).thenReturn(user);
        Mockito.when(bundle.getSession()).thenReturn(session);
        Mockito.when(session.getUser()).thenReturn(admin);

        assertThrows(NumberFormatException.class, ()-> addCustomerCommand.execute(new String[] {"", "Hans", "2-3-1999", "1234", "56z65", "1122"}));
        assertThrows(NumberFormatException.class, ()-> addCustomerCommand.execute(new String[] {"", "Hans", "2-3-1999", "AbCde", "5665", "1122"}));
        assertThrows(InvalidInputException.class, ()-> addCustomerCommand.execute(new String[] {}));

        Mockito.when(session.getUser()).thenReturn(customer);

        assertThrows(InvalidUserException.class, ()-> addCustomerCommand.execute(new String[] {"", "Hans", "2-3-1999", "1234", "5665", "112A"}));
    }

    @Test
    void addCustomerCommandHelp() {
        assertEquals("Adds new customer                                                          <Command> <Name> <Birth Day> <Social Number> <ID> <pin> | Commands: add",
                addCustomerCommand.help());
    }
}