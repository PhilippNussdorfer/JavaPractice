package bbrz.textadventure.rooms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LocationPointerTest {

    @Mock
    Location location;

    LocationPointer pointer;

    @BeforeEach
    void setUp() {
        pointer = new LocationPointer("s", location);
    }

    @Test
    void isDirection() {
        var res = pointer.isDirection("w");
        assertFalse(res);

        res = pointer.isDirection("s");
        assertTrue(res);
    }

    @Test
    void getTarget() {
        var res = pointer.getTarget();
        assertEquals(location, res);
    }
}