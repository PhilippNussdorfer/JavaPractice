package bbrz.textadventure;

import bbrz.textadventure.colors.TextColor;
import bbrz.textadventure.customException.RoomNotFoundException;
import bbrz.textadventure.rooms.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GameTest {

    Game game;
    @Mock
    Player player;
    @Mock
    Location location;
    @Mock
    Location secLocation;
    @Mock
    OutputWrapper wrapper;

    @BeforeEach
    void setUp() {
        game = new Game(player, location, wrapper);
    }

    @Test
    void moveToNextRoom() throws RoomNotFoundException {
        Mockito.when(location.getRoom("s")).thenReturn(secLocation);
        Mockito.when(secLocation.getRoom("n")).thenReturn(location);

        game.move("s");
        assertEquals(game.getCurrentLocation(), secLocation);

        game.move("n");
        assertEquals(game.getCurrentLocation(), location);
    }

    @Test
    void getPossibleDirections() {
        Mockito.when(location.getPointerDirections()).thenReturn(List.of("s => secLocation", "n => thirdLocation"));

        game.getPossibleDirections();
        Mockito.verify(wrapper, Mockito.times(1)).outPrintColored("\ns => secLocation", TextColor.GREEN);
        Mockito.verify(wrapper, Mockito.times(1)).outPrintColored("\nn => thirdLocation", TextColor.GREEN);
    }
}