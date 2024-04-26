package bbrz.textadventure.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AttackCalcTest {

    AttackCalc calc;

    @BeforeEach
    void setUp() {
        calc = new AttackCalc();
    }

    @Test
    void getsAttacked() {
        int res = calc.getsAttacked(100, 10);
        assertEquals(90, res);

        res = calc.getsAttacked(100, 100);
        assertEquals(1, res);
    }

    @Test
    void getDmgRoll() {
        for (int i = 0; i < 10; i++) {
            int res = calc.getDmgRoll(5);
            System.out.println(res);
            assertTrue(List.of(3, 5, 4).contains(res));
        }
    }
}