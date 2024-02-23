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
class MoveActionTest {

    @Mock
    Game game;

    MoveAction move;

    @BeforeEach
    void setUp() {
         move = new MoveAction(game, "w", "n", "o", "s");
    }

    @Test
    void moveRoom() {
        move.execute("s");
        Mockito.verify(game, Mockito.times(1)).move("s");

        move.execute("w");
        Mockito.verify(game, Mockito.times(1)).move("w");

        move.execute("o");
        Mockito.verify(game, Mockito.times(1)).move("o");

        move.execute("n");
        Mockito.verify(game, Mockito.times(1)).move("n");
    }

    @Test
    void notACommandForMove() {
        var res = move.canHandle("get-info");
        assertFalse(res);

        res = move.canHandle("w");
        assertTrue(res);
    }
}