package Bank.commandInterpretor;

import Bank.customExceptions.*;
import Bank.tools.StringFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InterpreterTest {

    Interpreter interpreter;

    @Mock
    HelpCommand helpCommand;
    @Mock
    CommandAbstract addCustomerCommand;
    @Mock
    StringFormatter formatter;
    @Mock
    CommandAbstract loginCommand;
    @Mock
    CommandAbstract transferCommand;
    @Mock
    CommandAbstract addAccountCommand;

    @BeforeEach
    void setUp() {
        interpreter = new Interpreter(formatter);
        interpreter.addCommand(addCustomerCommand, helpCommand, loginCommand, transferCommand, addAccountCommand);
    }

    @Test
    void addCommand() {
        assertEquals(5, interpreter.getCommands().size());
        assertTrue(interpreter.getCommands().contains(helpCommand));
        assertTrue(interpreter.getCommands().contains(addCustomerCommand));
        assertTrue(interpreter.getCommands().contains(loginCommand));
        assertTrue(interpreter.getCommands().contains(addAccountCommand));
    }

    @Test
    void interpret() throws NoBundleException, InvalidInputException, LoginFailedException, InvalidUserException, TransferFailedException, AccountTypeNotExisting, AccountTypeAlreadyExists {
        interpreter.interpret("");
        Mockito.verify(helpCommand, Mockito.times(1)).execute(null);

        Mockito.when(loginCommand.canHandle("login")).thenReturn(true);
        interpreter.interpret("login");
        Mockito.verify(loginCommand, Mockito.times(1)).execute(new String[] {"login"});
    }

    @Test
    void getHelpMessage() {
        interpreter.getHelpMessage();
        Mockito.verify(helpCommand, Mockito.times(1)).execute(null);
    }

    @Test
    void exceptionsOnInterpret() throws InvalidInputException, InvalidUserException, NoBundleException, LoginFailedException, TransferFailedException, AccountTypeNotExisting, AccountTypeAlreadyExists {
        Mockito.when(addCustomerCommand.canHandle("add")).thenReturn(true);
        Mockito.doThrow(new InvalidUserException("Invalid User")).when(addCustomerCommand).execute(Mockito.any(String[].class));
        interpreter.interpret("add f, g, h, i");
        Mockito.verify(formatter, Mockito.times(1)).outputWrapper("Invalid User");


        Mockito.doThrow(new InvalidInputException("Invalid Input")).when(addCustomerCommand).execute(Mockito.any(String[].class));
        interpreter.interpret("add f, g, h, i");
        Mockito.verify(formatter, Mockito.times(1)).outputWrapper("Invalid Input");


        Mockito.doThrow(new NumberFormatException("Not a Number")).when(addCustomerCommand).execute(Mockito.any(String[].class));
        interpreter.interpret("add f, g, h, i");
        Mockito.verify(formatter, Mockito.times(1)).outputWrapper("Not a Number");


        Mockito.doThrow(new NoBundleException("No Bundle Found")).when(addCustomerCommand).canHandle(Mockito.anyString());
        interpreter.interpret("add f, g, h, i");
        Mockito.verify(formatter, Mockito.times(1)).outputWrapper("No Bundle Found");


        Mockito.when(loginCommand.canHandle("login")).thenReturn(true);
        Mockito.doThrow(new LoginFailedException("Login Failed")).when(loginCommand).execute(Mockito.any(String[].class));
        interpreter.interpret("login f, g, h, i");
        Mockito.verify(formatter, Mockito.times(1)).outputWrapper("Login Failed");


        Mockito.when(transferCommand.canHandle("transfer")).thenReturn(true);
        Mockito.doThrow(new TransferFailedException("Transfer Failed")).when(transferCommand).execute(Mockito.any(String[].class));
        interpreter.interpret("transfer f, g, h, i");
        Mockito.verify(formatter, Mockito.times(1)).outputWrapper("Transfer Failed");

        Mockito.when(addAccountCommand.canHandle("addAcc")).thenReturn(true);
        Mockito.doThrow(new AccountTypeNotExisting("Account does not exist")).when(addAccountCommand).execute(Mockito.any(String[].class));
        interpreter.interpret("addAcc f, g, h, i");
        Mockito.verify(formatter, Mockito.times(1)).outputWrapper("Account does not exist");

        Mockito.when(addAccountCommand.canHandle("addAcc")).thenReturn(true);
        Mockito.doThrow(new AccountTypeAlreadyExists("Account exists already")).when(addAccountCommand).execute(Mockito.any(String[].class));
        interpreter.interpret("addAcc f, g, h, i");
        Mockito.verify(formatter, Mockito.times(1)).outputWrapper("Account exists already");
    }
}