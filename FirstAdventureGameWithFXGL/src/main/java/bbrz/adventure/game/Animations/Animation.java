package bbrz.adventure.game.Animations;

import bbrz.adventure.game.InputRegister.Direction;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import lombok.Getter;

@Getter
public class Animation {
    private final AnimationChannel myAnimation;
    private final AnimationIndicator animationIndicator;
    private final Direction direction;
    private final AnimatedTexture animatedTexture;

    public Animation(AnimationChannel myAnimation, AnimationIndicator playerAnimationIndicator, Direction direction,AnimatedTexture animatedTexture) {
        this.myAnimation = myAnimation;
        this.animationIndicator = playerAnimationIndicator;
        this.direction = direction;
        this.animatedTexture = animatedTexture;
    }

    public void loopAnimation() {
        if (this.animatedTexture.getAnimationChannel() != myAnimation) {
            this.animatedTexture.loopAnimationChannel(myAnimation);
        }
    }
}
