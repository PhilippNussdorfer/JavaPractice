package bbrz.textadventure.rooms;

import bbrz.textadventure.Interpreter;
import bbrz.textadventure.actions.Action;
import bbrz.textadventure.customException.CommandNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InterpreterTest {

    @Mock
    Action exitAction;
    @Mock
    Action moveAction;

    Interpreter interpreter;

    @BeforeEach
    void setUp() {
        interpreter = new Interpreter();
        interpreter.addActions(exitAction, moveAction);
    }

    @Test
    void interpretSingleWordAction() throws CommandNotFoundException {

        Mockito.when(exitAction.canHandle("exit")).thenReturn(true);
        interpreter.interpret("exit");
        Mockito.verify(exitAction, Mockito.times(1)).execute("exit");
    }

    @Test
    void singleWordActionWithMorePossibleCommands() throws CommandNotFoundException {

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
    void commandDoseNotExist() {
        assertThrows(CommandNotFoundException.class, ()-> interpreter.interpret("Hello"));
    }
}