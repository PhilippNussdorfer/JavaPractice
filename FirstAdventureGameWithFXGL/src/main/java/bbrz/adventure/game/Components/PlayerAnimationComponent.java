package bbrz.adventure.game.Components;

import bbrz.adventure.game.Animations.Animation;
import bbrz.adventure.game.Animations.AnimationIndicator;
import bbrz.adventure.game.Animations.InterpretAnimation;
import bbrz.adventure.game.InputRegister.Direction;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

import java.util.List;

public class PlayerAnimationComponent extends Component {

    private final String playerGraphic = "player/player_move_";
    private final AnimationChannel left = new AnimationChannel(FXGL.image(playerGraphic+"left.png"), Duration.seconds(0.5), 4);
    private final AnimationChannel right = new AnimationChannel(FXGL.image(playerGraphic+"right.png"), Duration.seconds(0.5), 4);
    private final AnimationChannel up = new AnimationChannel(FXGL.image(playerGraphic+"up.png"), Duration.seconds(0.5), 4);
    private final AnimationChannel down = new AnimationChannel(FXGL.image(playerGraphic+"down.png"), Duration.seconds(0.5), 4);
    private final AnimationChannel idleDown = new AnimationChannel(FXGL.image(playerGraphic+"idle_down.png"), Duration.seconds(1), 4);
    private final AnimationChannel idleLeft = new AnimationChannel(FXGL.image(playerGraphic+"idle_left.png"), Duration.seconds(1), 4);
    private final AnimationChannel idleRight = new AnimationChannel(FXGL.image(playerGraphic+"idle_right.png"), Duration.seconds(1), 4);
    private final AnimationChannel idleUp = new AnimationChannel(FXGL.image(playerGraphic+"idle_up.png"), Duration.seconds(1), 4);
    private final AnimationChannel runLeft = new AnimationChannel(FXGL.image(playerGraphic+"run_left.png"), Duration.seconds(0.5), 6);
    private final AnimationChannel runRight = new AnimationChannel(FXGL.image(playerGraphic+"run_right.png"), Duration.seconds(0.5), 6);
    private final AnimationChannel runUp = new AnimationChannel(FXGL.image(playerGraphic+"run_up.png"), Duration.seconds(0.5), 6);
    private final AnimationChannel runDown = new AnimationChannel(FXGL.image(playerGraphic+"run_down.png"), Duration.seconds(0.5), 6);
    private final AnimatedTexture texture = new AnimatedTexture(idleDown);
    private InterpretAnimation interpretAnimation;
    private final double x;
    private final double y;

    public PlayerAnimationComponent(double x, double y) {
        this.x = x;
        this.y = y;
        initInterpret();
    }

    private void initInterpret() {
        interpretAnimation  = new InterpretAnimation(List.of(
                new Animation(left, AnimationIndicator.NORMAL, Direction.LEFT, texture),
                new Animation(right, AnimationIndicator.NORMAL, Direction.RIGHT, texture),
                new Animation(up, AnimationIndicator.NORMAL, Direction.UP, texture),
                new Animation(down, AnimationIndicator.NORMAL, Direction.DOWN, texture),

                new Animation(idleLeft, AnimationIndicator.IDLE, Direction.LEFT, texture),
                new Animation(idleRight, AnimationIndicator.IDLE, Direction.RIGHT, texture),
                new Animation(idleUp, AnimationIndicator.IDLE, Direction.UP, texture),
                new Animation(idleDown, AnimationIndicator.IDLE, Direction.DOWN, texture),

                new Animation(runLeft, AnimationIndicator.RUN, Direction.LEFT, texture),
                new Animation(runRight, AnimationIndicator.RUN, Direction.RIGHT, texture),
                new Animation(runUp, AnimationIndicator.RUN, Direction.UP, texture),
                new Animation(runDown, AnimationIndicator.RUN, Direction.DOWN, texture)
        ));
    }

    public void loopAnimation(Direction direction, AnimationIndicator animationIndicator) {
        interpretAnimation.interpret(direction, animationIndicator);
    }

    public void respawn() {
        entity.removeFromWorld();
        FXGL.spawn("player", new SpawnData(this.x, this.y));
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        texture.loopAnimationChannel(idleDown);
    }
}
