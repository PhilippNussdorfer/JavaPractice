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
class RoomTest {

    @Mock
    RoomPointer pointer;
    @Mock
    RoomPointer secPointer;
    Room room;
    Room secRoom;

    @BeforeEach
    void setUp() {
        room = new Room("startRoom", "description", pointer);
        secRoom = new Room("secRoom", "description", secPointer);
    }

    @Test
    void getRoom() throws RoomNotFoundException {
        Mockito.when(pointer.isDirection("s")).thenReturn(true);
        Mockito.when(pointer.getTarget()).thenReturn(secRoom);

        var res = room.getRoom("s");
        assertEquals(secRoom, res);

        Mockito.when(secPointer.isDirection("n")).thenReturn(true);
        Mockito.when(secPointer.getTarget()).thenReturn(room);

        res = secRoom.getRoom("n");
        assertEquals(room, res);
    }

    @Test
    void noRoomFound() {
        assertThrows(RoomNotFoundException.class, ()-> room.getRoom("n"));
    }
}