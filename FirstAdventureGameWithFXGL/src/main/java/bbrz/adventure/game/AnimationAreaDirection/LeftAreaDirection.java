package bbrz.adventure.game.AnimationAreaDirection;

import bbrz.adventure.game.InputRegister.Direction;
import com.almasb.fxgl.entity.Entity;

public class LeftAreaDirection extends AreaDirection {

    public LeftAreaDirection(Entity mainEntity, Entity attackArea) {
        super(mainEntity, attackArea, 90, -40, 0, Direction.LEFT);
    }
}
