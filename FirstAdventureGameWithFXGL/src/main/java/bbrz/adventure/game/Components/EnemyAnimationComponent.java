package bbrz.adventure.game.Components;

import bbrz.adventure.game.Items.Item;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

import java.util.List;

public class EnemyAnimationComponent extends Component {
    private final String enemyName;
    private AnimationChannel idle;
    private AnimationChannel getsDmg;
    private AnimationChannel death;
    private AnimationChannel move;
    private AnimatedTexture animatedTexture;
    private boolean getsDamaged = false;

    public EnemyAnimationComponent(String enemyName) {
        this.enemyName = enemyName;
        initAnimationChannels();
    }

    private EnemyComponent getComponent() {
        return entity.getComponent(EnemyComponent.class);
    }

    private void initAnimationChannels() {
        this.idle  = new AnimationChannel(FXGL.getAssetLoader().loadImage("enemys/" + this.enemyName + "/spriteSheets/Idle.png"), Duration.seconds(0.5), 11);
        this.getsDmg = new AnimationChannel(FXGL.getAssetLoader().loadImage("enemys/" + this.enemyName + "/spriteSheets/Hit.png"), Duration.seconds(0.5), 8);
        this.death = new AnimationChannel(FXGL.getAssetLoader().loadImage("enemys/" + this.enemyName + "/spriteSheets/Dead.png"), Duration.seconds(0.5), 15);
        this.move = new AnimationChannel(FXGL.getAssetLoader().loadImage("enemys/" + this.enemyName + "/spriteSheets/Walk.png"), Duration.seconds(0.5), 13);
        this.animatedTexture  = new AnimatedTexture(idle);
    }

    public void checkIfDead(boolean hasZeroHp) {
        if (hasZeroHp) {
            getsDamaged = true;
            this.animatedTexture.loopAnimationChannel(death);
            FXGL.getGameTimer().runOnceAfter(() -> {
                if (entity != null) {
                    getComponent().respawn();
                    getsDamaged = false;
                    dropItemBag();
                }
            }, Duration.seconds(0.5));
        } else {
            getsDamaged = true;
            this.animatedTexture.loopAnimationChannel(getsDmg);
            FXGL.getGameTimer().runOnceAfter(() -> {
                this.animatedTexture.loopAnimationChannel(idle);
                getsDamaged = false;
            }, Duration.seconds(0.5));
        }
    }

    private void dropItemBag() {
        List<Item> droppedItems = getComponent().getInterpretDrops().interpret();

        var bag = FXGL.spawn("itembag", entity.getX() -15, entity.getY() -10);
        bag.getComponent(ItemBagComponent.class).addDroppedItems(droppedItems);
        /*FXGL.getGameTimer().runOnceAfter(() -> {
            bag.getComponent(ItemBagComponent.class).checkIfItemBagIsOpen();
            bag.removeFromWorld();
        }, Duration.seconds(120));*/
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(animatedTexture);
        animatedTexture.loopAnimationChannel(idle);
    }

    public void idle() {
        if (animatedTexture.getAnimationChannel() != idle && !getsDamaged)
            animatedTexture.loopAnimationChannel(idle);
    }

    public void move() {
        if (animatedTexture.getAnimationChannel() != move && !getsDamaged)
            animatedTexture.loopAnimationChannel(move);
    }
}
