package Bank.commandInterpretor;

import Bank.BankManagementSystem;
import Bank.Bundle;
import Bank.accounts.Account;
import Bank.accounts.GiroAccount;
import Bank.customExceptions.InvalidInputException;
import Bank.customExceptions.InvalidUserException;
import Bank.customExceptions.NoBundleException;
import Bank.person.Admin;
import Bank.person.Customer;
import Bank.person.Session;
import Bank.person.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommandAbstractTest {

    AddCustomerCommand addCustomerCommand = new AddCustomerCommand("add");
    ChangeLimitCommand changeLimitCommand = new ChangeLimitCommand("change");
    DepositCommand depositCommand = new DepositCommand("deposit");
    ExitCommand exitCommand = new ExitCommand("exit");
    HelpCommand helpCommand = new HelpCommand("help");
    LoginCommand loginCommand = new LoginCommand("login");
    LogoutCommand logoutCommand = new LogoutCommand("logout");
    TransferCommand transferCommand = new TransferCommand("transfer");
    WithdrawCommand withdrawCommand = new WithdrawCommand("withdraw");

    @Mock
    Bundle bundle;
    @Mock
    Admin admin;
    @Mock
    User user;
    @Mock
    Customer customer_1;
    @Mock
    Customer customer_2;
    @Mock
    BankManagementSystem system;
    @Mock
    Session session;
    @Mock
    GiroAccount giro;
    @Mock
    Account account;
    @Captor
    ArgumentCaptor<Boolean> boolCaptor;
    @Mock
    Interpreter interpreter;
    @Mock
    CommandAbstract command;

    @BeforeEach
    void setUp() {
        changeLimitCommand.addBundle(bundle);
        depositCommand.addBundle(bundle);
        exitCommand.addBundle(bundle);
        helpCommand.addBundle(bundle);
        loginCommand.addBundle(bundle);
        logoutCommand.addBundle(bundle);
        transferCommand.addBundle(bundle);
        withdrawCommand.addBundle(bundle);
    }

    @Test
    void canHandle() throws NoBundleException {
        assertThrows(NoBundleException.class, ()-> addCustomerCommand.canHandle("add"));

        addCustomerCommand.addBundle(bundle);
        assertTrue(addCustomerCommand.canHandle("add"));
        assertFalse(addCustomerCommand.canHandle("addCustomer"));

        assertTrue(changeLimitCommand.canHandle("change"));
        assertFalse(changeLimitCommand.canHandle("addCustomer"));

        assertTrue(exitCommand.canHandle("exit"));
        assertFalse(exitCommand.canHandle("addCustomer"));

        assertTrue(helpCommand.canHandle("help"));
        assertFalse(helpCommand.canHandle("addCustomer"));

        assertTrue(loginCommand.canHandle("login"));
        assertFalse(loginCommand.canHandle("addCustomer"));

        assertTrue(logoutCommand.canHandle("logout"));
        assertFalse(logoutCommand.canHandle("addCustomer"));

        assertTrue(transferCommand.canHandle("transfer"));
        assertFalse(transferCommand.canHandle("addCustomer"));

        assertTrue(withdrawCommand.canHandle("withdraw"));
        assertFalse(withdrawCommand.canHandle("addCustomer"));
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

    @Test
    void changeLimitCommand() throws InvalidUserException, InvalidInputException {
        Mockito.when(bundle.getSession()).thenReturn(session);
        Mockito.when(session.getUser()).thenReturn(customer_1);
        Mockito.when(customer_1.getAccount(Mockito.anyString())).thenReturn(giro);

        changeLimitCommand.execute(new String[] {"", "135.87"});
        Mockito.verify(giro, Mockito.times(1)).setLimit(135.87);
    }

    @Test
    void exceptionsOnChangeLimitCommand() {
        Mockito.when(bundle.getSession()).thenReturn(session);
        Mockito.when(session.getUser()).thenReturn(admin);

        assertThrows(InvalidUserException.class, ()-> changeLimitCommand.execute(new String[] {"", "2"}));
        assertThrows(InvalidInputException.class, ()-> changeLimitCommand.execute(new String[] {}));

        Mockito.when(session.getUser()).thenReturn(customer_1);

        assertThrows(NumberFormatException.class, ()-> changeLimitCommand.execute(new String[] {"", "a"}));
    }

    @Test
    void changeLimitCommandHelp() {
        assertEquals("Changes the limit of your giro account.                                    <Command> <limit>                                  | Commands: change",
                changeLimitCommand.help());
    }

    @Test
    void exitCommand() {
        exitCommand.execute(new String[] {""});

        Mockito.verify(bundle, Mockito.times(1)).setRunning(boolCaptor.capture());
        assertFalse(boolCaptor.getValue());
    }

    @Test
    void exitCommandHelp() {
        assertEquals("Exits the program                                                          <Command>                                          | Commands: exit", exitCommand.help());
    }

    @Test
    void helpCommand() {
        Mockito.when(bundle.getInterpreter()).thenReturn(interpreter);
        Mockito.when(interpreter.getCommands()).thenReturn(List.of(command));

        helpCommand.execute(new String[] {""});

        Mockito.verify(interpreter, Mockito.times(1)).getCommands();
    }

    @Test
    void hepCommandHelp() {
        assertEquals("Help command shows all possible commands                                   <Command>                                          | Commands: help", helpCommand.help());
    }
}