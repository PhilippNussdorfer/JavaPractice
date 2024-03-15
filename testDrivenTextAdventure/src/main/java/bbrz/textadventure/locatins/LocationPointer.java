package bbrz.textadventure.locatins;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LocationPointer {
    private String direction;
    private Location target;

    public boolean isDirection(String direction) {
        char[] directionChars = direction.toLowerCase().toCharArray();
        return this.direction.toCharArray()[0] == directionChars[0];
    }

}
