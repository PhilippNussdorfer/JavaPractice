package bbrz.textadventure;

import bbrz.textadventure.rooms.Room;
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
    Room room;
    @Mock
    Room secRoom;

    @BeforeEach
    void setUp() {
        game = new Game(interpreter, player, room);
    }

    @Test
    void moveToNextRoom() {
        Mockito.when(room.getRoom("s")).thenReturn(secRoom);
        Mockito.when(secRoom.getRoom("n")).thenReturn(room);

        game.move("s");
        assertEquals(game.getCurrentRoom(), secRoom);

        game.move("n");
        assertEquals(game.getCurrentRoom(), room);
    }
}