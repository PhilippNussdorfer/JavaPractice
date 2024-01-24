package bbrez.at.NewSpringBootTryWithInjection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GiveNotesTest {
    public static final String aName = "Max";
    private GiveGrades giveGrades;

    @BeforeEach
    void setUp() {
        giveGrades = new GiveGrades();
    }

    @Test
    void StudentNameInResult() {
        Result result = giveGrades.res(aName);
        assertEquals(aName, result.getStudentName());
    }

    @Test
    void noteShouldBeBetweenOneAndFive() {
        for (int i = 0; i < 5; i++) {
            Result res = giveGrades.res(aName);
            assertTrue(res.getResult() >= 1 && res.getResult() <= 5);
        }
    }

    @Test
    void nullNameGivenShouldThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> giveGrades.res(null));
    }

    @Test
    void GivenNameEmptyShouldThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> giveGrades.res(""));
        assertThrows(IllegalArgumentException.class, () -> giveGrades.res(" "));
        assertThrows(IllegalArgumentException.class, ()-> giveGrades.res("  "));
    }
}