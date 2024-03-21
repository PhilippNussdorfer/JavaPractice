package bbrz.adventure.game.Components;

import bbrz.adventure.game.EnemyDrops.EnemyDrops;
import bbrz.adventure.game.EnemyDrops.InterpretDrops;
import bbrz.adventure.game.EntityType;
import bbrz.adventure.game.Items.ItemDropList;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.util.Duration;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class EnemyComponent extends Component {
    private final double x;
    private final double y;
    private final Random rnd = new Random();
    @Getter
    private double hp;
    @Getter
    private final int speed = 120;
    private final double maxHp;
    private final int maxDmg;
    private final int minDmg;
    @Getter
    private final EnemyIndicator indicator;
    @Getter
    private final InterpretDrops interpretDrops = new InterpretDrops(
            List.of(new EnemyDrops(EnemyIndicator.SKELETON, ItemDropList.getSkeletonDrops()),
                    new EnemyDrops(EnemyIndicator.SPIDER, ItemDropList.getSpiderDrops()),
                    new EnemyDrops(EnemyIndicator.GOBLIN, ItemDropList.getGoblinDrops()),
                    new EnemyDrops(EnemyIndicator.WOLF, ItemDropList.getWolfDrops())),
            this);

    public EnemyComponent(double x, double y, int minDmg, int maxDmg, int hp, EnemyIndicator indicator) {
        this.maxDmg = maxDmg;
        this.minDmg = minDmg;
        this.hp = hp;
        this.maxHp = hp;
        this.indicator = indicator;
        this.x = x;
        this.y = y;
    }

    private EnemyAnimationComponent getAnimationComponent() {
        return entity.getComponent(EnemyAnimationComponent.class);
    }

    private PhysicsComponent getPhysicsComponent() {
        return entity.getComponent(PhysicsComponent.class);
    }

    private Entity getPlayer() {
        return getGameWorld().getSingleton(EntityType.PLAYER);
    }

    @Override
    public void onUpdate(double tpf) {
        followPlayerIfInRange(150, 30, 20);
    }

    public void followPlayerIfInRange(int maxRange, int minRange, int evadeDistance) {
        var isEvading = evadeEntity(getEntityThatIsToClose(evadeDistance));

        if (!isEvading) {
            if (entity.distanceBBox(getPlayer()) <= maxRange && entity.distanceBBox(getPlayer()) > minRange) {
                moveTowards(getPlayer());
            } else {
                stopMoving();
            }
        }
    }

    private void stopMoving() {
        getAnimationComponent().idle();
        getPhysicsComponent().setVelocityX(0);
        getPhysicsComponent().setVelocityY(0);
    }

    private void moveTowards(Entity target) {
        double angle = getAngle(target, entity);
        move(angle);
    }

    private double getAngle(Entity first, Entity second) {
        double dx = first.getX() - second.getX();
        double dy = first.getY() - second.getY();

        return Math.atan2(dy, dx);
    }

    private void move(double angle) {
        getPhysicsComponent().setVelocityX(speed * Math.cos(angle));
        getPhysicsComponent().setVelocityY(speed * Math.sin(angle));
        getAnimationComponent().move();
    }

    private void moveAround() {

    }

    private boolean evadeEntity(List<Entity> entitysToEvade) {
        if (entitysToEvade != null) {

        }

        return !(entitysToEvade == null);
    }

    private List<Entity> getEntityThatIsToClose(int distance) {
        List<Entity> entitys = getGameWorld().getEntities();
        List<Entity> entitysToClose = new ArrayList<>();

        for (Entity entity : entitys) {
            if (entity != this.entity && entity != getPlayer()) {
                if (this.entity.distanceBBox(entity) < distance && entity.getType() != EntityType.ITEM_BAG) {
                    entitysToClose.add(entity);
                }
            }
        }
        return entitysToClose;
    }

    public void respawn() {
        entity.removeFromWorld();
        FXGL.getGameTimer().runOnceAfter(() -> FXGL.spawn(this.indicator.getValue(), new SpawnData(this.x, this.y)), Duration.seconds(30));
    }

    public int getDamage() {
        return rnd.nextInt(maxDmg - minDmg) + minDmg;
    }


    public boolean incHp(double incValue) {
        this.hp += incValue;
        if (this.hp <= 0) {
            return true;
        }
        return false;
    }

    public void regen() {
        if (this.hp < this.maxHp) {
            this.hp += 0.01;
        } if (this.hp > this.maxHp) {
            this.hp = this.hp - 0.01;
        }
    }
}
