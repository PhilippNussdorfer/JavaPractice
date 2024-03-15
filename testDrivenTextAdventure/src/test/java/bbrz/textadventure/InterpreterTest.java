package bbrz.textadventure;

import bbrz.textadventure.actions.Action;
import bbrz.textadventure.customException.CommandNotFoundException;
import bbrz.textadventure.customException.NoFreeSpaceException;
import bbrz.textadventure.customException.NoItemFoundException;
import bbrz.textadventure.tools.Interpreter;
import jdk.jshell.spi.ExecutionControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InterpreterTest {

    @Mock
    Action exitAction;
    @Mock
    Action moveAction;
    @Mock
    Action pickup;

    Interpreter interpreter;

    @BeforeEach
    void setUp() {
        interpreter = new Interpreter();
        interpreter.addActions(exitAction, moveAction, pickup);
    }

    @Test
    void interpretSingleWordAction() throws CommandNotFoundException, ExecutionControl.NotImplementedException, NoFreeSpaceException, NoItemFoundException {

        Mockito.when(exitAction.canHandle("exit")).thenReturn(true);
        interpreter.interpret("exit");
        Mockito.verify(exitAction, Mockito.times(1)).execute("exit");
    }

    @Test
    void singleWordActionWithMorePossibleCommands() throws CommandNotFoundException, ExecutionControl.NotImplementedException, NoFreeSpaceException, NoItemFoundException {

        Mockito.when(moveAction.canHandle("north")).thenReturn(true);
        Mockito.when(moveAction.canHandle("south")).thenReturn(true);
        Mockito.when(moveAction.canHandle("west")).thenReturn(true);
        Mockito.when(moveAction.canHandle("east")).thenReturn(true);

        interpreter.interpret("south");
        Mockito.verify(moveAction, Mockito.times(1)).execute("south");

        interpreter.interpret("north");
        Mockito.verify(moveAction, Mockito.times(1)).execute("north");

        interpreter.interpret("east");
        Mockito.verify(moveAction, Mockito.times(1)).execute("east");

        interpreter.interpret("west");
        Mockito.verify(moveAction, Mockito.times(1)).execute("west");
    }

    @Test
    void commandWithMultipleWords() throws CommandNotFoundException, ExecutionControl.NotImplementedException, NoFreeSpaceException, NoItemFoundException {
        Mockito.when(pickup.canHandle("pickup")).thenReturn(true);

        interpreter.interpret("pickup log stick");
        Mockito.verify(pickup, Mockito.times(1)).execute("pickup", "log", "stick");
    }

    @Test
    void commandDoseNotExist() {
        assertThrows(CommandNotFoundException.class, ()-> interpreter.interpret("Hello"));
    }
}