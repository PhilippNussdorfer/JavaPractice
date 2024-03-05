package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BackpackActionTest {

    BackpackAction action;

    @Mock
    Game game;

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
    void canHandle() {
        assertTrue(action.canHandle("BACKPACK"));
        assertFalse(action.canHandle("Pickup"));
    }
}