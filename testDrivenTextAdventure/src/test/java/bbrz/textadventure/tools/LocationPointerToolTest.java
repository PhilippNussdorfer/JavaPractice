package bbrz.textadventure.tools;

import bbrz.textadventure.locations.Location;
import bbrz.textadventure.locations.LocationPointer;
import bbrz.textadventure.locations.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LocationPointerToolTest {

    LocationPointerTool tool = new LocationPointerTool();

    @Mock
    Location current;
    @Mock
    Location prev;
    @Mock
    Position prevPos;
    @Mock
    Position currentPos;
    @Captor
    ArgumentCaptor<LocationPointer> locationCaptor;

    @Test
    void pointerRight() {
        Mockito.when(current.getPos()).thenReturn(currentPos);
        Mockito.when(currentPos.getX()).thenReturn(1);
        Mockito.when(currentPos.getY()).thenReturn(0);

        Mockito.when(prev.getPos()).thenReturn(prevPos);
        Mockito.when(prevPos.getX()).thenReturn(0);
        Mockito.when(prevPos.getY()).thenReturn(0);

        tool.addPointerToLocation(prev, current);

        Mockito.verify(prev, Mockito.times(1)).addPointers(locationCaptor.capture());
        assertEquals("E", locationCaptor.getValue().getDirection());
        assertEquals(current, locationCaptor.getValue().getTarget());

        Mockito.verify(current, Mockito.times(1)).addPointers(locationCaptor.capture());
        assertEquals("W", locationCaptor.getValue().getDirection());
        assertEquals(prev, locationCaptor.getValue().getTarget());
    }

    @Test
    void pointerLeft() {
        Mockito.when(current.getPos()).thenReturn(currentPos);
        Mockito.when(currentPos.getX()).thenReturn(0);
        Mockito.when(currentPos.getY()).thenReturn(0);

        Mockito.when(prev.getPos()).thenReturn(prevPos);
        Mockito.when(prevPos.getX()).thenReturn(1);
        Mockito.when(prevPos.getY()).thenReturn(0);

        tool.addPointerToLocation(prev, current);

        Mockito.verify(prev, Mockito.times(1)).addPointers(locationCaptor.capture());
        assertEquals("W", locationCaptor.getValue().getDirection());
        assertEquals(current, locationCaptor.getValue().getTarget());

        Mockito.verify(current, Mockito.times(1)).addPointers(locationCaptor.capture());
        assertEquals("E", locationCaptor.getValue().getDirection());
        assertEquals(prev, locationCaptor.getValue().getTarget());
    }

    @Test
    void pointerUp() {
        Mockito.when(current.getPos()).thenReturn(currentPos);
        Mockito.when(currentPos.getX()).thenReturn(0);
        Mockito.when(currentPos.getY()).thenReturn(0);

        Mockito.when(prev.getPos()).thenReturn(prevPos);
        Mockito.when(prevPos.getX()).thenReturn(0);
        Mockito.when(prevPos.getY()).thenReturn(1);

        tool.addPointerToLocation(prev, current);

        Mockito.verify(prev, Mockito.times(1)).addPointers(locationCaptor.capture());
        assertEquals("N", locationCaptor.getValue().getDirection());
        assertEquals(current, locationCaptor.getValue().getTarget());

        Mockito.verify(current, Mockito.times(1)).addPointers(locationCaptor.capture());
        assertEquals("S", locationCaptor.getValue().getDirection());
        assertEquals(prev, locationCaptor.getValue().getTarget());
    }

    @Test
    void pointerDown() {
        Mockito.when(current.getPos()).thenReturn(currentPos);
        Mockito.when(currentPos.getX()).thenReturn(0);
        Mockito.when(currentPos.getY()).thenReturn(1);

        Mockito.when(prev.getPos()).thenReturn(prevPos);
        Mockito.when(prevPos.getX()).thenReturn(0);
        Mockito.when(prevPos.getY()).thenReturn(0);

        tool.addPointerToLocation(prev, current);

        Mockito.verify(prev, Mockito.times(1)).addPointers(locationCaptor.capture());
        assertEquals("S", locationCaptor.getValue().getDirection());
        assertEquals(current, locationCaptor.getValue().getTarget());

        Mockito.verify(current, Mockito.times(1)).addPointers(locationCaptor.capture());
        assertEquals("N", locationCaptor.getValue().getDirection());
        assertEquals(prev, locationCaptor.getValue().getTarget());
    }
}