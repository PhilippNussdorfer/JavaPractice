package bbrz.textadventure.locatins;

import bbrz.textadventure.locations.Location;
import bbrz.textadventure.locations.LocationPointer;
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
    LocationPointer secPointer;

    @BeforeEach
    void setUp() {
        pointer = new LocationPointer("s", location);
        secPointer = new LocationPointer("W", location);
    }

    @Test
    void isDirection() {
        var res = pointer.isDirection("w");
        assertFalse(res);

        res = pointer.isDirection("S");
        assertTrue(res);

        res = secPointer.isDirection("w");
        assertTrue(res);

        res = secPointer.isDirection("East");
        assertFalse(res);
    }

    @Test
    void getTarget() {
        var res = pointer.getTarget();
        assertEquals(location, res);
    }
}