package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import bbrz.textadventure.tools.StringFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ExitActionTest {

    ExitAction action;

    @Mock
    Game game;
    @Mock
    StringFormatter formatter;

    @BeforeEach
    void setUp() {
        action = new ExitAction(game, "ex", "x", "exit");
    }

    @Test
    void executeCommands() {
        action.execute("ex");
        Mockito.verify(game, Mockito.times(1)).setLoopGame(false);

        action.execute("x");
        Mockito.verify(game, Mockito.times(2)).setLoopGame(false);

        action.execute("exiT");
        Mockito.verify(game, Mockito.times(3)).setLoopGame(false);
    }

    @Test
    void reactionToValidAndInvalidCommands() {
        var res = action.canHandle("X");
        assertTrue(res);

        res = action.canHandle("hXit");
        assertFalse(res);
    }

    @Test
    void helpMessage() {
        Mockito.when(game.getFormatter()).thenReturn(formatter);
        Mockito.when(formatter.getPrintableCollection(Mockito.any(String[].class))).thenReturn("ex, x, exit                   ");
        Mockito.when(formatter.formatStringLength(Mockito.anyInt(), Mockito.anyString())).thenReturn("Exit           ", "Exits the game <Command>                                                                            ", "ex, x, exit                   ");

        assertEquals("Exit            => Exits the game <Command>                                                                             | Commands => ex, x, exit                   ", action.helpMessage());
    }
}