package bbrz.adventure.game.InputRegister;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class InputStatus {
    private final List <Direction> directionList = new ArrayList<>();
    private Direction lastKnownDirection = Direction.DOWN;

    public void setDirection(Direction direction) {
        directionList.add(direction);
        lastKnownDirection = direction;
    }

    public void unsetDirection(Direction direction) {
        directionList.remove(direction);
    }

    public boolean isIdle() {
        return directionList.isEmpty();
    }
}
