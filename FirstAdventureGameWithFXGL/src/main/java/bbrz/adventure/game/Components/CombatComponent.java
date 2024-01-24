package bbrz.adventure.game.Components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

public class CombatComponent extends Component {

    AnimationChannel slash = new AnimationChannel(FXGL.getAssetLoader().loadImage("slash/small_slash_2.png"), Duration.seconds(0.1), 4);
    AnimatedTexture animated = new AnimatedTexture(slash);

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(animated);
        animated.loopAnimationChannel(slash);

        FXGL.getGameTimer().runOnceAfter(() ->{
            entity.removeFromWorld();
        }, Duration.seconds(0.1));
    }
}
