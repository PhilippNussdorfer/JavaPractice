package bbrz.textadventure;

import bbrz.textadventure.rooms.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Game {
    private Interpreter interpreter;
    private Player player;
    private Room currentRoom;

    public void move(String direction) {
        currentRoom = currentRoom.getRoom(direction);
    }
}
