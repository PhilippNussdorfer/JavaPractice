package at.bbrz;

import at.bbrz.calculations.Interpretor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CalculatorTest {

    @Mock
    SwitchBetweenArabianAndRomanNumberSystem switcher;
    @Mock
    Interpretor interpretor;
    @Mock
    IllegalArgumentException exception;

    Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator(switcher, interpretor);
    }

    @Test
    void add() {
        Mockito.when(switcher.getArabianNumber(Mockito.anyString())).thenReturn(1001, 4);
        Mockito.when(interpretor.interpret(Mockito.anyInt(), Mockito.anyChar(), Mockito.anyInt())).thenReturn(1005);
        Mockito.when(switcher.getRomanNumber(Mockito.anyInt())).thenReturn("MV");

        var res = calculator.Calculate("Mi", '+', "Iv");
        assertEquals("MV", res);

        Mockito.when(switcher.getArabianNumber(Mockito.anyString())).thenReturn(2, 5);
        Mockito.when(interpretor.interpret(Mockito.anyInt(), Mockito.anyChar(), Mockito.anyInt())).thenReturn(7);
        Mockito.when(switcher.getRomanNumber(Mockito.anyInt())).thenReturn("IIIX");

        res = calculator.Calculate("II", '+', "V");
        assertEquals("IIIX", res);
    }

    @Test
    void subtract() {
        Mockito.when(switcher.getArabianNumber(Mockito.anyString())).thenReturn(101, 15);
        Mockito.when(interpretor.interpret(Mockito.anyInt(), Mockito.anyChar(), Mockito.anyInt())).thenReturn(86);
        Mockito.when(switcher.getRomanNumber(Mockito.anyInt())).thenReturn("XXCVI");

        var res = calculator.Calculate("ci", '-', "xv");
        assertEquals("XXCVI", res);

        Mockito.when(switcher.getArabianNumber(Mockito.anyString())).thenReturn(5, 2);
        Mockito.when(interpretor.interpret(Mockito.anyInt(), Mockito.anyChar(), Mockito.anyInt())).thenReturn(3);
        Mockito.when(switcher.getRomanNumber(Mockito.anyInt())).thenReturn("III");

        res = calculator.Calculate("V", '-', "ii");
        assertEquals("III", res);
    }

    @Test
    void multiplei() {
        Mockito.when(switcher.getArabianNumber(Mockito.anyString())).thenReturn(5, 7);
        Mockito.when(interpretor.interpret(Mockito.anyInt(), Mockito.anyChar(), Mockito.anyInt())).thenReturn(35);
        Mockito.when(switcher.getRomanNumber(Mockito.anyInt())).thenReturn("XXXV");

        var res = calculator.Calculate("V", '*', "VII");
        assertEquals("XXXV", res);

        Mockito.when(switcher.getArabianNumber(Mockito.anyString())).thenReturn(2, 5);
        Mockito.when(interpretor.interpret(Mockito.anyInt(), Mockito.anyChar(), Mockito.anyInt())).thenReturn(10);
        Mockito.when(switcher.getRomanNumber(Mockito.anyInt())).thenReturn("X");

        res = calculator.Calculate("II", '*', "V");
        assertEquals("X", res);
    }

    @Test
    void div() {
        Mockito.when(switcher.getArabianNumber(Mockito.anyString())).thenReturn(1000, 10);
        Mockito.when(interpretor.interpret(Mockito.anyInt(), Mockito.anyChar(), Mockito.anyInt())).thenReturn(100);
        Mockito.when(switcher.getRomanNumber(Mockito.anyInt())).thenReturn("C");

        var res = calculator.Calculate("M", '/', "X");
        assertEquals("C", res);

        Mockito.when(switcher.getArabianNumber(Mockito.anyString())).thenReturn(10, 5);
        Mockito.when(interpretor.interpret(Mockito.anyInt(), Mockito.anyChar(), Mockito.anyInt())).thenReturn(2);
        Mockito.when(switcher.getRomanNumber(Mockito.anyInt())).thenReturn("II");

        res = calculator.Calculate("X", '/', "V");
        assertEquals("II", res);
    }

    @Test
    void OperatorDoseNotExist() {
        Mockito.when(switcher.getArabianNumber(Mockito.anyString())).thenReturn(1000, 1000);
        Mockito.when(interpretor.interpret(Mockito.anyInt(), Mockito.anyChar(), Mockito.anyInt())).thenThrow(exception);

        assertThrows(IllegalArgumentException.class, () -> calculator.Calculate("M", 'i', "M"));
    }
}