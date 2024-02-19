package bbrz.textadventure;

import bbrz.textadventure.customException.RoomNotFoundException;
import bbrz.textadventure.rooms.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GameTest {

    Game game;
    @Mock
    Interpreter interpreter;
    @Mock
    Player player;
    @Mock
    Location location;
    @Mock
    Location secLocation;

    @BeforeEach
    void setUp() {
        game = new Game(interpreter, player, location);
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
}