package at.bbrz;

import at.bbrz.calculations.Interpretor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CalculatorTest {

    Calculator calculator = new Calculator(new SwitchBetweenArabianAndRomanNumberSystem(), new Interpretor());

    @BeforeEach
    void setUp() {
    }

    @Test
    void add() {
        var res = calculator.Calculate("Mi", '+', "Iv");
        assertEquals("MV", res);

        res = calculator.Calculate("II", '+', "V");
        assertEquals("IIIX", res);
    }

    @Test
    void subtract() {
        var res = calculator.Calculate("ci", '-', "xv");
        assertEquals("XXCVI", res);

        res = calculator.Calculate("V", '-', "ii");
        assertEquals("III", res);
    }

    @Test
    void multiplei() {
        var res = calculator.Calculate("V", '*', "VII");
        assertEquals("XXXV", res);

        res = calculator.Calculate("II", '*', "V");
        assertEquals("X", res);
    }

    @Test
    void div() {
        var res = calculator.Calculate("M", '/', "X");
        assertEquals("C", res);

        res = calculator.Calculate("X", '/', "V");
        assertEquals("II", res);
    }

    @Test
    void OperatorDoseNotExist() {
        var res = calculator.Calculate("m", 'i', "I");
        assertEquals("N", res);
    }
}