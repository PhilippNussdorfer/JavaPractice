package bbrz.textadventure.rooms;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RoomPointer {
    private String direction;
    private Room target;

    public boolean isDirection(String direction) {
        return direction.equals(this.direction);
    }

    public Room getTarget() {
        return this.target;
    }
}
