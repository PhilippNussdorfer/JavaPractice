package Bank;

import Bank.commandInterpretor.Interpreter;
import Bank.tools.StringFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BankManagementSystemTest {

    BankManagementSystem system;

    @Mock
    Bundle bundle;
    @Mock
    Scanner scanner;
    @Mock
    StringFormatter formatter;
    @Mock
    Interpreter interpreter;

    @BeforeEach
    void setUp() {
        system = new BankManagementSystem(scanner, formatter);
    }

    @Test
    void lunch() {
        system.updateBundle(bundle);

        Mockito.when(scanner.nextLine()).thenReturn("help");
        Mockito.when(bundle.getInterpreter()).thenReturn(interpreter);
        Mockito.when(bundle.isRunning()).thenReturn(true, false);

        system.lunch();

        Mockito.verify(interpreter, Mockito.times(1)).getHelpMessage();
        Mockito.verify(formatter, Mockito.times(1)).outputWrapper("Input:");
        Mockito.verify(interpreter, Mockito.times(1)).interpret("help");
        Mockito.verify(formatter, Mockito.times(1)).outputWrapper("");
    }

    @Test
    void updateBundle() {
        system.updateBundle(bundle);

        assertEquals(bundle, system.getBundle());
    }

    @Test
    void getter() {
        assertNull(system.getUser());
        assertEquals(scanner, system.getScanner());
        assertEquals(formatter, system.getFormatter());
    }
}