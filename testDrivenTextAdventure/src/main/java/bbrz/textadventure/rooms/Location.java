package bbrz.textadventure.rooms;

import bbrz.textadventure.customException.RoomNotFoundException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Location {

    @Getter
    private final String name;
    @Getter
    private final String description;
    private final List<LocationPointer> pointers = new ArrayList<>();

    public Location(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Location getRoom(String direction) throws RoomNotFoundException {
        for (LocationPointer pointer : pointers) {
            if (pointer.isDirection(direction)) {
                return pointer.getTarget();
            }
        }
        throw new RoomNotFoundException("There is no room in this direction.");
    }

    public void addPointers(LocationPointer ... pointers) {
        this.pointers.addAll(List.of(pointers));
    }
}