package Bank.commandInterpretor;

import Bank.BankManagementSystem;
import Bank.Bundle;
import Bank.customExceptions.InvalidInputException;
import Bank.customExceptions.LoginFailedException;
import Bank.customExceptions.NoBundleException;
import Bank.person.Admin;
import Bank.person.Customer;
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
class LoginCommandTest {

    LoginCommand loginCommand = new LoginCommand("login");

    @Mock
    Admin admin;
    @Mock
    Customer customer;
    @Mock
    BankManagementSystem system;
    @Mock
    Bundle bundle;
    @Mock
    User user;

    @BeforeEach
    void setUp() {
        loginCommand.setBundle(bundle);
    }

    @Test
    void canHandle() throws NoBundleException {
        assertTrue(loginCommand.canHandle("Login"));
        assertFalse(loginCommand.canHandle("help"));
    }

    @Test
    void execute() throws InvalidInputException, LoginFailedException {
        Mockito.when(bundle.getSystem()).thenReturn(system);
        Mockito.when(system.getUser()).thenReturn(user);
        Mockito.when(user.getUsers()).thenReturn(List.of(admin, customer));

        Mockito.when(admin.getEmployeeID()).thenReturn(12L);
        Mockito.when(admin.login("12")).thenReturn(true);

        loginCommand.execute(new String[] {"", "12", "12"});
        Mockito.verify(system, Mockito.times(1)).updateBundle(Mockito.any(Bundle.class));

        Mockito.when(customer.getCustomerID()).thenReturn(11L);
        Mockito.when(customer.login("11")).thenReturn(true);

        loginCommand.execute(new String[] {"", "11", "11"});
        Mockito.verify(system, Mockito.times(2)).updateBundle(Mockito.any(Bundle.class));
    }

    @Test
    void failedLogin() {
        Mockito.when(bundle.getSystem()).thenReturn(system);
        Mockito.when(system.getUser()).thenReturn(user);
        Mockito.when(user.getUsers()).thenReturn(List.of(admin, customer));

        assertThrows(LoginFailedException.class, ()-> loginCommand.execute(new String[] {"", "12", "11"}));
        assertThrows(LoginFailedException.class, ()-> loginCommand.execute(new String[] {"", "11", "12"}));
        assertThrows(LoginFailedException.class, ()-> loginCommand.execute(new String[] {"", "", ""}));
        assertThrows(LoginFailedException.class, ()-> loginCommand.execute(new String[] {"", "12d", "12"}));
        assertThrows(LoginFailedException.class, ()-> loginCommand.execute(new String[] {"", "12", "12d"}));
        assertThrows(LoginFailedException.class, ()-> loginCommand.execute(new String[] {"", "12b", "12b"}));
    }

    @Test
    void help() {
        assertEquals("Login with pin and ID                                                      <Command> <ID> <Pin>                               | Commands: login", loginCommand.help());
    }

    @Test
    void exceptions() {
        assertThrows(InvalidInputException.class, ()-> loginCommand.execute(new String[] {"", "12"}));
        assertThrows(InvalidInputException.class, ()-> loginCommand.execute(new String[] {}));

        loginCommand.setBundle(null);
        assertThrows(NoBundleException.class, ()-> loginCommand.canHandle(""));
    }
}