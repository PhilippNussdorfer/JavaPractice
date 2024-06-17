package Bank.commandInterpretor;

import Bank.Bundle;
import Bank.customExceptions.NoBundleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.CapturesArguments;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ExitCommandTest {

    ExitCommand exitCommand = new ExitCommand("exit");

    @Mock
    Bundle bundle;
    @Captor
    ArgumentCaptor<Boolean> boolCaptor;

    @BeforeEach
    void setUp() {
        exitCommand.setBundle(bundle);
    }

    @Test
    void canHandle() throws NoBundleException {
        assertTrue(exitCommand.canHandle("Exit"));
        assertFalse(exitCommand.canHandle("x"));
    }

    @Test
    void execute() {
        exitCommand.execute(new String[] {""});

        Mockito.verify(bundle, Mockito.times(1)).setRunning(boolCaptor.capture());
        assertFalse(boolCaptor.getValue());
    }

    @Test
    void exceptions() {
        exitCommand.setBundle(null);

        assertThrows(NoBundleException.class, ()-> exitCommand.canHandle("EXIT"));
    }

    @Test
    void help() {
        assertEquals("Exits the program                                                          <Command>                                          | Commands: exit", exitCommand.help());
    }
}