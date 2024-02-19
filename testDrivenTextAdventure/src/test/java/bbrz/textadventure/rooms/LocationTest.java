package bbrz.textadventure.rooms;

import bbrz.textadventure.customException.RoomNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LocationTest {

    @Mock
    LocationPointer pointer;
    @Mock
    LocationPointer secPointer;
    Location location;
    Location secLocation;

    @BeforeEach
    void setUp() {
        location = new Location("startRoom", "description");
        secLocation = new Location("secRoom", "description");

        location.addPointers(pointer);
        secLocation.addPointers(secPointer);
    }

    @Test
    void getRoom() throws RoomNotFoundException {
        Mockito.when(pointer.isDirection("s")).thenReturn(true);
        Mockito.when(pointer.getTarget()).thenReturn(secLocation);

        var res = location.getRoom("s");
        assertEquals(secLocation, res);

        Mockito.when(secPointer.isDirection("n")).thenReturn(true);
        Mockito.when(secPointer.getTarget()).thenReturn(location);

        res = secLocation.getRoom("n");
        assertEquals(location, res);
    }

    @Test
    void noRoomFound() {
        assertThrows(RoomNotFoundException.class, ()-> location.getRoom("n"));
    }
}