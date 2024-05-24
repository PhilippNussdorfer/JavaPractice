package bbrz.textadventure.locations;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LocationPointer {
    private Directions direction;
    private Location target;

    public boolean isDirection(String direction) {
        return this.direction.getValues().contains(direction.toLowerCase());
    }
}
