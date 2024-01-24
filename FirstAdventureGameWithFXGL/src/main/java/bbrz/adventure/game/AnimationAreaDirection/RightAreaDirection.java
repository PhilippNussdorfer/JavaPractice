package bbrz.adventure.game.AnimationAreaDirection;

import bbrz.adventure.game.InputRegister.Direction;
import com.almasb.fxgl.entity.Entity;

public class RightAreaDirection extends AreaDirection {

    public RightAreaDirection(Entity mainEntity, Entity attackArea) {
        super(mainEntity, attackArea, 270, 0, 0, Direction.RIGHT);
    }
}
