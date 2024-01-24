package bbrz.adventure.game.AnimationAreaDirection;

import bbrz.adventure.game.InputRegister.Direction;
import com.almasb.fxgl.entity.Entity;

public class UpAreaDirection extends AreaDirection {

    public UpAreaDirection(Entity mainEntity, Entity attackArea) {
        super(mainEntity, attackArea, 180, -20, -20, Direction.UP);
    }
}
