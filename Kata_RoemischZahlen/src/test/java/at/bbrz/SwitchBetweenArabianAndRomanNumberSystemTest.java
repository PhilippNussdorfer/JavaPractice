package at.bbrz;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SwitchBetweenArabianAndRomanNumberSystemTest {

    @Mock
    SwitchBetweenArabianAndRomanNumberSystem switcher;

    @BeforeEach
    void setUp() {
        switcher = new SwitchBetweenArabianAndRomanNumberSystem();
    }

    @Test
    void getRomanNumber() {
        var result = switcher.getRomanNumber(1921);
        assertEquals("MCMXXI", result);

        result = switcher.getRomanNumber(1571);
        assertEquals("MDLXXI", result);

        result = switcher.getRomanNumber(4000);
        assertEquals("N", result);

        result = switcher.getRomanNumber(3999);
        assertEquals("Mmmcmxcix".toUpperCase(), result);

        result = switcher.getRomanNumber(1022);
        assertEquals("MXxII".toUpperCase(), result);

        result = switcher.getRomanNumber(1896);
        assertEquals("MDCCCxCVI".toUpperCase(), result);
    }

    @Test
    void goThroughAllPossibleNumbers() {
        for (int i = 0; i < 4000; i++) {
            var resRom = switcher.getRomanNumber(i);
            var resArab = switcher.getArabianNumber(resRom);
            assertEquals(i , resArab);
        }
    }

    @Test
    void getArabianNumber() {

        var result = switcher.getArabianNumber("MXxII");
        assertEquals(1022, result);

        result = switcher.getArabianNumber("Mmmcmxcix");
        assertEquals(3999, result);

        result = switcher.getArabianNumber("MDXXxci");
        assertEquals(1571, result);

        result = switcher.getArabianNumber("mmmm");
        assertEquals(4000, result);

        result = switcher.getArabianNumber("MCMXXI");
        assertEquals(1921, result);

        result = switcher.getArabianNumber("MccMxCVI");
        assertEquals(1896, result);
    }

    @Test
    void throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, ()-> switcher.getArabianNumber("AM"));
    }
}