package at.bbrz.calculations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InterpretorTest {

    Interpretor interpretor = new Interpretor();

    @BeforeEach
    void setUp() {
    }

    @Test
    void interpret() {
        var res = interpretor.interpret(1, '+', 3);
        assertEquals(4, res);

        res = interpretor.interpret(7, '-', 3);
        assertEquals(4, res);

        res = interpretor.interpret(2, '*', 3);
        assertEquals(6, res);

        res = interpretor.interpret(9, '/', 3);
        assertEquals(3, res);
    }

    @Test
    void
    operatorDoseNotExist() {
        assertThrows(IllegalArgumentException.class, ()-> interpretor.interpret(145, '(', 334));
    }
}