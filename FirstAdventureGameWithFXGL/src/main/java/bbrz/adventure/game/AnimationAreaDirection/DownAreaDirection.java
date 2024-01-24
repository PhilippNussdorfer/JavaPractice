package bbrz.adventure.game.AnimationAreaDirection;

import bbrz.adventure.game.InputRegister.Direction;
import com.almasb.fxgl.entity.Entity;

public class DownAreaDirection extends AreaDirection {

    public DownAreaDirection(Entity mainEntity, Entity attackArea) {
        super(mainEntity, attackArea, 0, -10, 15, Direction.DOWN);
    }
}
