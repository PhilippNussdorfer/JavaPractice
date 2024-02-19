package bbrz.textadventure.rooms;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LocationPointer {
    private String direction;
    private Location target;

    public boolean isDirection(String direction) {
        return direction.equals(this.direction);
    }

    public Location getTarget() {
        return this.target;
    }
}
