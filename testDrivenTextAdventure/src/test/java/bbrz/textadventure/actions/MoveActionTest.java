package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

class MoveActionTest {

    @Mock
    Game game;

    MoveAction move = new MoveAction(game);

    @BeforeEach
    void setUp() {
    }
}