package bbrz.textadventure.rooms;

import bbrz.textadventure.customException.RoomNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Room {

    @Getter
    private final String name;
    @Getter
    private final String description;
    private final RoomPointer[] pointers;

    public Room getRoom(String direction) throws RoomNotFoundException {
        for (RoomPointer pointer : pointers) {
            if (pointer.isDirection(direction)) {
                return pointer.getTarget();
            }
        }
        throw new RoomNotFoundException("There is no room in this direction.");
    }
}
