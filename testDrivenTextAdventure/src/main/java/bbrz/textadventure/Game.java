package bbrz.textadventure;

import bbrz.textadventure.customException.RoomNotFoundException;
import bbrz.textadventure.rooms.Room;
import lombok.Getter;

@Getter
public class Game {
    private final Interpreter interpreter;
    private final Player player;
    private Room currentRoom;

    public Game(Interpreter interpreter, Player player, Room currentRoom) {
        this.interpreter = interpreter;
        this.player = player;
        this.currentRoom = currentRoom;
    }

    public void move(String direction) {
        try {
            currentRoom = currentRoom.getRoom(direction);

        } catch (RoomNotFoundException roomNotFound) {
            System.out.println(roomNotFound.getMessage());

        }
    }
}
