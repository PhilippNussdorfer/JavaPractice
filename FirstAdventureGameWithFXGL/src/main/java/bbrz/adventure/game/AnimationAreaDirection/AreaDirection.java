package bbrz.adventure.game.AnimationAreaDirection;

import bbrz.adventure.game.InputRegister.Direction;
import com.almasb.fxgl.entity.Entity;
import lombok.Getter;

public abstract class AreaDirection {

    private final Entity mainEntity;
    private final Entity attackArea;
    private final double rotation;
    private final double distanceX;
    private final double distanceY;
    @Getter
    private final Direction indicator;

    public AreaDirection(Entity mainEntity, Entity attackArea, double rotation, double distanceX, double distanceY, Direction indicator) {
        this.mainEntity = mainEntity;
        this.attackArea = attackArea;
        this.rotation = rotation;
        this.distanceX = distanceX;
        this.distanceY = distanceY;
        this.indicator = indicator;
    }

    public void setAttackArea() {
        attackArea.setRotation(rotation);
        attackArea.setPosition(mainEntity.getPosition().getX() + distanceX, mainEntity.getPosition().getY() + distanceY);
    }
}
