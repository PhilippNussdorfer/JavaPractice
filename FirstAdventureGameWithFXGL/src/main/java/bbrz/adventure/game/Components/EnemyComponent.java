package bbrz.adventure.game.Components;

import bbrz.adventure.game.ComputerEnemyBehavior.BehaviorComponent;
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

import java.util.List;
import java.util.Random;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class EnemyComponent extends Component {
    private final double x;
    private final double y;
    private final Random rnd = new Random();
    @Getter
    private double hp;
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

        var a = FXGL.getGameScene().getGameWorld();
    }

    private EnemyAnimationComponent getAnimationComponent() {
        return entity.getComponent(EnemyAnimationComponent.class);
    }

    private BehaviorComponent getBehavior() {
        return entity.getComponent(BehaviorComponent.class);
    }

    private PhysicsComponent getPhysicsComponent() {
        return entity.getComponent(PhysicsComponent.class);
    }

    private Entity getPlayer() {
        return getGameWorld().getSingleton(EntityType.PLAYER);
    }

    @Override
    public void onUpdate(double tpf) {
        move();
    }

    private void move() {
        var moveTowards = getBehavior().follow(getPlayer(), getAnimationComponent(), entity, 4.5, FXGL.getGameWorld().getEntities());
        getPhysicsComponent().setBodyLinearVelocity(moveTowards);
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
