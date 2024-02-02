package bbrz.textadventure.rooms;

import bbrz.textadventure.Interpreter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InterpreterTest {

    Interpreter interpreter = new Interpreter();

    @BeforeEach
    void setUp() {

    }

    @Test
    void moveRooms() {
        var nextRoom = interpreter.interpretRoom("S");
        assertTrue(nextRoom instanceof Clearing);

        nextRoom = interpreter.interpret("d");
        assertTrue(nextRoom instanceof );

        nextRoom = interpreter.interpret("a");
        assertEquals("Clearing", nextRoom.name);
    }

    @Test
    void noRoomToGo() {
        assertThrows(IllegalArgumentException.class, ()-> interpreter.interpret("W"));
    }
}