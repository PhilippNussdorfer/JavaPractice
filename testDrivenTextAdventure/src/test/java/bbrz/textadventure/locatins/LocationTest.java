package bbrz.textadventure.locatins;

import bbrz.textadventure.customException.RoomNotFoundException;
import bbrz.textadventure.rules.MapRuleMark;
import bbrz.textadventure.item.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LocationTest {

    Location location;
    Location secLocation;
    @Mock
    LocationPointer pointer;
    @Mock
    LocationPointer secPointer;
    @Mock
    Item item;
    @Mock
    Item secItem;

    @BeforeEach
    void setUp() {
        location = new Location("startRoom", "description", MapRuleMark.LAKE);
        secLocation = new Location("secRoom", "description", MapRuleMark.BEACH);

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
    void getPointerDirections() {
        Mockito.when(pointer.getDirection()).thenReturn("n");
        Mockito.when(pointer.getTarget()).thenReturn(secLocation);

        var res = location.getPointerDirections();
        assertEquals(res.get(0), "n => secRoom");
    }

    @Test
    void noRoomFound() {
        assertThrows(RoomNotFoundException.class, ()-> location.getRoom("n"));
    }

    @Test
    void addItem() {
        location.addItems(item);

        assertTrue(location.getItems().contains(item));
    }

    @Test
    void addItems() {
        location.addItems(item, secItem);

        assertTrue(location.getItems().contains(item));
        assertTrue(location.getItems().contains(secItem));
    }

    @Test
    void removeItem() {
        location.addItems(item, secItem);
        location.removeItems(item);

        assertEquals(1, location.getItems().size());
    }

    @Test
    void removeItems() {
        location.addItems(item, secItem);
        location.removeItems(secItem, item);

        assertEquals(0, location.getItems().size());
    }

    @Test
    void cloneLocation() {
        var res = location.cloneLocation();

        assertEquals(res.getMark(), location.getMark());
        assertEquals(res.getName(), location.getName());
        assertEquals(res.getDescription(), location.getDescription());
    }
}