package bbrz.textadventure.gameLoader;

import bbrz.textadventure.Game;
import bbrz.textadventure.tools.OutputWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InterpreterInitTest {
    @Mock
    Game game;
    @Mock
    OutputWrapper wrapper;

    @Test
    void initInterpreter() {
        var interpreter = InterpreterInit.initActionInterpreter(game, wrapper);

        assertEquals(11, interpreter.getActionList().size());
    }

    @Test
    void initRuleInterpreter() {
        var rules = InterpreterInit.initRuleInterpreter();

        assertEquals(12, rules.getRules().size());
    }
}