package bbrz.textadventure.rules;

import bbrz.textadventure.locations.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RuleInterpreterTest {

    TestMapRule rule = new TestMapRule();
    RuleInterpreter interpreter = new RuleInterpreter();

    @Mock
    Location loc;

    @BeforeEach
    void setUp() {
        interpreter.addList(List.of(rule));
    }

    @Test
    void addList() {
        interpreter.addList(List.of(rule));

        assertEquals(2 , interpreter.getRules().size());
        assertTrue(interpreter.getRules().contains(rule));
    }

    @Test
    void interpretRule() {
        Mockito.when(loc.getMark()).thenReturn(MapRuleMark.REPLACEABLE);
        assertTrue(interpreter.interpretRule(loc.getMark(), MapRuleMark.CLIFF));

        Mockito.when(loc.getMark()).thenReturn(MapRuleMark.LAKE);
        assertFalse(interpreter.interpretRule(loc.getMark(), MapRuleMark.CLIFF));

        Mockito.when(loc.getMark()).thenReturn(MapRuleMark.REPLACEABLE);
        assertFalse(interpreter.interpretRule(loc.getMark(), MapRuleMark.REPLACEABLE));
    }

    private static class TestMapRule implements Rule {

        @Override
        public boolean canHandle(MapRuleMark prevLocation) {
            return prevLocation == MapRuleMark.REPLACEABLE;
        }

        @Override
        public boolean isInRules(MapRuleMark randomLocation) {
            return randomLocation != MapRuleMark.REPLACEABLE;
        }
    }
}