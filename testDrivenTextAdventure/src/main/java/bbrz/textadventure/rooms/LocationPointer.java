package bbrz.textadventure.rooms;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LocationPointer {
    private String direction;
    private Location target;

    public boolean isDirection(String direction) {
        return direction.equals(this.direction);
    }

}
