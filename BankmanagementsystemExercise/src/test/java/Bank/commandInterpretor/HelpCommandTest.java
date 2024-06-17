package Bank.commandInterpretor;

import Bank.Bundle;
import Bank.customExceptions.NoBundleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HelpCommandTest {

    HelpCommand helpCommand = new HelpCommand("help");

    @Mock
    Bundle bundle;
    @Mock
    Interpreter interpreter;
    @Mock
    CommandAbstract command;

    @BeforeEach
    void setUp() {
        helpCommand.setBundle(bundle);
    }

    @Test
    void canHandle() throws NoBundleException {
        assertTrue(helpCommand.canHandle("Help"));
        assertFalse(helpCommand.canHandle("EXIT"));
    }

    @Test
    void execute() {
        Mockito.when(bundle.getInterpreter()).thenReturn(interpreter);
        Mockito.when(interpreter.getCommands()).thenReturn(List.of(command));

        helpCommand.execute(new String[] {});

        Mockito.verify(interpreter, Mockito.times(1)).getCommands();
    }

    @Test
    void help() {
        assertEquals("Help command shows all possible commands                                   <Command>                                          | Commands: help", helpCommand.help());
    }

    @Test
    void exceptions() {
        helpCommand.setBundle(null);

        assertThrows(NoBundleException.class, ()-> helpCommand.canHandle("HELP"));
    }
}