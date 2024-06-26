package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import bbrz.textadventure.tools.StringFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BackpackActionTest {

    BackpackAction action;

    @Mock
    Game game;
    @Mock
    StringFormatter formatter;

    @BeforeEach
    void setUp() {
        action = new BackpackAction(game, "bp", "Backpack");
    }

    @Test
    void execute() {
        action.execute("bp");
        Mockito.verify(game, Mockito.times(1)).printBPItems();
    }

    @Test
    void getter() {
        var res = action.getName();
        assertEquals("Backpack", res);

        res = action.getDescription();
        assertEquals("Shows you your items in the backpack <Command>", res);

        var resArr = action.getPossibleCommands();

        assertTrue(Arrays.stream(resArr).toList().contains("bp"));
        assertTrue(Arrays.stream(resArr).toList().contains("Backpack"));
        assertFalse(Arrays.stream(resArr).toList().contains("exit"));
    }

    @Test
    void canHandle() {
        assertTrue(action.canHandle("BACKPACK"));
        assertFalse(action.canHandle("Pickup"));
    }

    @Test
    void helpMessage() {
        Mockito.when(game.getFormatter()).thenReturn(formatter);
        Mockito.when(formatter.formatStringLength(Mockito.anyInt(), Mockito.anyString())).thenReturn("Backpack       ", "Shows you your items in the backpack <Command>                                                      ", "bp, Backpack                  ");
        Mockito.when(formatter.getPrintableCollection(Mockito.any(String[].class))).thenReturn("bp, Backpack                  ");

        assertEquals("Backpack        => Shows you your items in the backpack <Command>                                                       | Commands => bp, Backpack                  ", action.helpMessage());
    }
}