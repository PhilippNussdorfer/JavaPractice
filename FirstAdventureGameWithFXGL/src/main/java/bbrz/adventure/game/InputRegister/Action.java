package bbrz.adventure.game.InputRegister;

import bbrz.adventure.game.Animations.AnimationIndicator;
import bbrz.adventure.game.Components.ItemBagComponent;
import bbrz.adventure.game.Components.PlayerAnimationComponent;
import bbrz.adventure.game.Components.PlayerComponent;
import bbrz.adventure.game.EntityType;
import bbrz.adventure.game.MamblsAdventure;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.PhysicsComponent;
import org.jetbrains.annotations.NotNull;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class Action extends UserAction {

    private final InputStatus inputStatus;
    private final Direction direction;
    private final boolean isXAxis;
    private final boolean isPositive;

    public Action(@NotNull String name, InputStatus inputStatus, Direction direction, boolean isXAxis, boolean isPositive) {
        super(name);
        this.inputStatus = inputStatus;
        this.direction = direction;
        this.isXAxis = isXAxis;
        this.isPositive = isPositive;
    }

    @Override
    protected void onActionBegin() {
        FXGL.set("lastPointLookedAt", 0);
        inputStatus.setDirection(direction);
    }

    @Override
    protected void onAction() {
        AnimationIndicator animationIndicator;
        getPlayer().getComponent(PlayerComponent.class).isMoving = true;

        if (FXGL.geti("runningActive") != 0) {
            if (FXGL.geti("isRunning") != 1)
                FXGL.set("isRunning", 1);
        }

        if (FXGL.geti("isRunning") == 1) {
            animationIndicator = AnimationIndicator.RUN;
        } else {
            animationIndicator = AnimationIndicator.NORMAL;
        }

        if (MamblsAdventure.existsEntity(EntityType.ITEM_BAG)) {
            FXGL.getGameWorld().getClosestEntity(getPlayer(), MamblsAdventure.isEntityType)
            .ifPresent(entity -> entity.getComponent(ItemBagComponent.class).checkIfItemBagIsOpen());
        }
        getAxis(getPlusOrMinusVelocity());
        getPlayer().getComponent(PlayerAnimationComponent.class).loopAnimation(direction, animationIndicator);
    }

    @Override
    protected void onActionEnd() {
        inputStatus.unsetDirection(direction);
        getAxis(0);
        getPlayer().getComponent(PlayerAnimationComponent.class).loopAnimation(direction, AnimationIndicator.IDLE);
        getPlayer().getComponent(PlayerComponent.class).isMoving = false;

        if (FXGL.geti("isRunning") != 0)
            FXGL.set("isRunning", 0);
    }

    private Entity getPlayer() {
        return getGameWorld().getSingleton(EntityType.PLAYER);
    }

    private void getAxis(int velocity) {
        if (isXAxis) {
            getPlayer().getComponent(PhysicsComponent.class).setVelocityX(velocity);
        } else {
            getPlayer().getComponent(PhysicsComponent.class).setVelocityY(velocity);
        }
    }

    private int getPlusOrMinusVelocity() {
        if (isPositive) {
            return getPlayer().getComponent(PlayerComponent.class).getPlayerMoveSpeed();
        }
        return -getPlayer().getComponent(PlayerComponent.class).getPlayerMoveSpeed();
    }
}
