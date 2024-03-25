package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import bbrz.textadventure.tools.Interpreter;
import bbrz.textadventure.tools.OutputWrapper;
import bbrz.textadventure.colors.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HelpActionTest {

    HelpAction action;
    @Mock
    Game game;
    @Mock
    OutputWrapper wrapper;
    @Mock
    DescriptionAction secAction;
    @Mock
    Action thirdAction;
    @Mock
    Interpreter interpreter;

    @BeforeEach
    void setUp() {
        action = new HelpAction(game, "h", "help");
    }

    @Test
    void executeOnceDescribe() {
        Mockito.when(game.getInterpreter()).thenReturn(interpreter);
        Mockito.when(game.getWrapper()).thenReturn(wrapper);
        Mockito.when(interpreter.getActionList()).thenReturn(List.of(secAction));
        Mockito.when(secAction.helpMessage()).thenReturn("name            => description                                                                                          | Commands => Commands                       | Additions => addition                      ");
        
        action.execute("h");
        Mockito.verify(wrapper, Mockito.times(1)).outPrintlnColored("=".repeat(210), TextColor.DARK_BROWN);
        Mockito.verify(wrapper, Mockito.times(1)).outPrintlnColored("=".repeat(210) + "\n", TextColor.DARK_BROWN);
        Mockito.verify(wrapper, Mockito.times(1)).outPrintlnColored("name            => description                                                                                          | Commands => Commands                       | Additions => addition                      ", TextColor.CYAN);
    }

    @Test
    void executeOtherAction() {
        Mockito.when(game.getInterpreter()).thenReturn(interpreter);
        Mockito.when(game.getWrapper()).thenReturn(wrapper);

        Mockito.when(interpreter.getActionList()).thenReturn(List.of(thirdAction));
        Mockito.when(thirdAction.helpMessage()).thenReturn("name            => description                                                                                          | Commands => Commands                      ");

        action.execute("h");
        Mockito.verify(wrapper, Mockito.times(1)).outPrintlnColored("name            => description                                                                                          | Commands => Commands                      ", TextColor.CYAN);
    }

    @Test
    void executeMultipleActions() {
        Mockito.when(game.getInterpreter()).thenReturn(interpreter);
        Mockito.when(game.getWrapper()).thenReturn(wrapper);

        Mockito.when(interpreter.getActionList()).thenReturn(List.of(thirdAction, secAction));
        Mockito.when(secAction.helpMessage()).thenReturn("name            => description                                                                                          | Commands => Commands                       | Additions => addition                      ");
        Mockito.when(thirdAction.helpMessage()).thenReturn("name            => description                                                                                          | Commands => Commands                      ");

        action.execute("h");
        Mockito.verify(wrapper, Mockito.times(1)).outPrintlnColored("name            => description                                                                                          | Commands => Commands                      ", TextColor.CYAN);
        Mockito.verify(wrapper, Mockito.times(1)).outPrintlnColored("name            => description                                                                                          | Commands => Commands                       | Additions => addition                      ", TextColor.CYAN);
    }

    @Test
    void canHandle() {
        assertTrue(action.canHandle("HelP"));
        assertFalse(action.canHandle("exiT"));
    }

    @Test
    void helpMessage() {
        assertEquals("Help            => Shows the help for all possible Commands <Command>                                                   | Commands => h, help                       ", action.helpMessage());
    }
}