package bbrz.textadventure.gameLoader;

import bbrz.textadventure.Game;
import bbrz.textadventure.tools.OutputWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Wrapper;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InterpreterInitTest {
    @Mock
    Game game;
    @Mock
    OutputWrapper wrapper;

    @Test
    void init() {
        var interpreter = InterpreterInit.init(game, wrapper);

        assertEquals(11, interpreter.getActionList().size());
    }
}