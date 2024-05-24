package bbrz.textadventure;

import bbrz.textadventure.actions.Action;
import bbrz.textadventure.customException.CommandNotFoundException;
import bbrz.textadventure.customException.NoFreeSpaceException;
import bbrz.textadventure.customException.NoItemFoundException;
import bbrz.textadventure.tools.CommandInterpreter;
import jdk.jshell.spi.ExecutionControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommandInterpreterTest {

    @Mock
    Action exitAction;
    @Mock
    Action moveAction;
    @Mock
    Action pickup;

    CommandInterpreter commandInterpreter;

    @BeforeEach
    void setUp() {
        commandInterpreter = new CommandInterpreter();
        commandInterpreter.addActions(exitAction, moveAction, pickup);
    }

    @Test
    void interpretSingleWordAction() throws CommandNotFoundException, ExecutionControl.NotImplementedException, NoFreeSpaceException, NoItemFoundException {

        Mockito.when(exitAction.canHandle("exit")).thenReturn(true);
        commandInterpreter.interpret("exit");
        Mockito.verify(exitAction, Mockito.times(1)).execute("exit");
    }

    @Test
    void singleWordActionWithMorePossibleCommands() throws CommandNotFoundException, ExecutionControl.NotImplementedException, NoFreeSpaceException, NoItemFoundException {

        Mockito.when(moveAction.canHandle("north")).thenReturn(true);
        Mockito.when(moveAction.canHandle("south")).thenReturn(true);
        Mockito.when(moveAction.canHandle("west")).thenReturn(true);
        Mockito.when(moveAction.canHandle("east")).thenReturn(true);

        commandInterpreter.interpret("south");
        Mockito.verify(moveAction, Mockito.times(1)).execute("south");

        commandInterpreter.interpret("north");
        Mockito.verify(moveAction, Mockito.times(1)).execute("north");

        commandInterpreter.interpret("east");
        Mockito.verify(moveAction, Mockito.times(1)).execute("east");

        commandInterpreter.interpret("west");
        Mockito.verify(moveAction, Mockito.times(1)).execute("west");
    }

    @Test
    void commandWithMultipleWords() throws CommandNotFoundException, ExecutionControl.NotImplementedException, NoFreeSpaceException, NoItemFoundException {
        Mockito.when(pickup.canHandle("pickup")).thenReturn(true);

        commandInterpreter.interpret("pickup log stick");
        Mockito.verify(pickup, Mockito.times(1)).execute("pickup", "log", "stick");
    }

    @Test
    void commandDoseNotExist() {
        assertThrows(CommandNotFoundException.class, ()-> commandInterpreter.interpret("Hello"));
    }
}