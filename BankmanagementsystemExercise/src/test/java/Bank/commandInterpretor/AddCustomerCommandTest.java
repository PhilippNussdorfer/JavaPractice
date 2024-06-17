package Bank.commandInterpretor;

import Bank.BankManagementSystem;
import Bank.Bundle;
import Bank.customExceptions.InvalidInputException;
import Bank.customExceptions.NoBundleException;
import Bank.person.Customer;
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

    @Test
    void canHandle() throws NoBundleException {
        assertThrows(NoBundleException.class, ()-> addCustomerCommand.canHandle("add"));

        addCustomerCommand.addBundle(bundle);
        assertTrue(addCustomerCommand.canHandle("add"));
        assertFalse(addCustomerCommand.canHandle("addCustomer"));
    }

    @Test
    void AddCommand() throws InvalidInputException {
        addCustomerCommand.addBundle(bundle);

        Mockito.when(bundle.getSystem()).thenReturn(system);
        Mockito.when(system.getUser()).thenReturn(user);

        addCustomerCommand.execute(new String[] {"", "Hans", "2-3-1999", "1234", "5665", "112A"});
        Mockito.verify(user, Mockito.times(1)).addUser(Mockito.any(Customer.class));
    }

    @Test
    void ExceptionOnAddCommand() {
        addCustomerCommand.addBundle(bundle);

        Mockito.when(bundle.getSystem()).thenReturn(system);
        Mockito.when(system.getUser()).thenReturn(user);

        assertThrows(NumberFormatException.class, ()-> addCustomerCommand.execute(new String[] {"", "Hans", "2-3-1999", "1234", "56z65", "1122"}));
        assertThrows(NumberFormatException.class, ()-> addCustomerCommand.execute(new String[] {"", "Hans", "2-3-1999", "AbCde", "5665", "1122"}));
        assertThrows(InvalidInputException.class, ()-> addCustomerCommand.execute(new String[] {}));
    }

    @Test
    void addCustomerCommandHelp() {
        assertEquals("Adds new customer                                                          <Command> <Name> <Birth Day> <Social Number> <ID> <pin> | Commands: add",
                addCustomerCommand.help());
    }
}