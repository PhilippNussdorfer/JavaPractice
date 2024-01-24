package bbrz.adventure.game.Animations;

import bbrz.adventure.game.InputRegister.Direction;
import lombok.Getter;

import java.util.List;

@Getter
public class InterpretAnimation {

    private final List<Animation> animations;


    public InterpretAnimation(List<Animation> animations) {
        this.animations = animations;
    }

    public void interpret(Direction direction, AnimationIndicator animationIndicator) {
        for (Animation animation : animations) {
            if (animation.getAnimationIndicator() == animationIndicator) {
                if (animation.getDirection() == direction) {
                    animation.loopAnimation();
                }
            }
        }
    }
}
