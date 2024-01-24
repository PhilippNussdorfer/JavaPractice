package bbrz.adventure.game.InputRegister;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputStatusTest {

    private InputStatus inputStatus;

    @BeforeEach
    void setUp() {
        inputStatus  = new InputStatus();
    }

    @Test
    void addDirection() {
        inputStatus.setDirection(Direction.UP);
        assertSame(inputStatus.getLastKnownDirection(), Direction.UP);

        inputStatus.setDirection(Direction.DOWN);
        assertNotSame(inputStatus.getLastKnownDirection(), Direction.UP);

        assertTrue(inputStatus.getDirectionList().contains(Direction.UP));
        assertTrue(inputStatus.getDirectionList().contains(Direction.DOWN));
    }

    @Test
    void unsetDirection() {
        inputStatus.setDirection(Direction.UP);
        inputStatus.unsetDirection(Direction.UP);

        assertFalse(inputStatus.getDirectionList().contains(Direction.UP));
    }

    @Test
    void isIdle() {
        inputStatus.setDirection(Direction.DOWN);
        assertFalse(inputStatus.isIdle());

        inputStatus.unsetDirection(Direction.DOWN);
        assertTrue(inputStatus.isIdle());
    }
}