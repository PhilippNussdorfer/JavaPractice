package bbrz.textadventure.rooms;

import bbrz.textadventure.customException.RoomNotFoundException;
import lombok.Getter;

public class Room {

    @Getter
    private final String name;
    @Getter
    private final String description;
    private final RoomPointer[] pointers;

    public Room(String name, String description, RoomPointer ... pointers) {
        this.name = name;
        this.description = description;
        this.pointers = pointers;
    }

    public Room getRoom(String direction) throws RoomNotFoundException {
        for (RoomPointer pointer : pointers) {
            if (pointer.isDirection(direction)) {
                return pointer.getTarget();
            }
        }
        throw new RoomNotFoundException("There is no room in this direction.");
    }
}
