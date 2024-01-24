package bbrz.pacman.Pacman;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

public class PlayerComponent extends Component {

    private final AnimationChannel left = new AnimationChannel(FXGL.image("player-left.png"), Duration.seconds(0.5), 6);
    private final AnimationChannel right = new AnimationChannel(FXGL.image("player-right.png"), Duration.seconds(0.5), 6);
    private final AnimationChannel up = new AnimationChannel(FXGL.image("player-up.png"), Duration.seconds(0.5), 6);
    private final AnimationChannel down = new AnimationChannel(FXGL.image("player-down.png"), Duration.seconds(0.5), 6);

    private final AnimatedTexture texture = new AnimatedTexture(up);

    private final double x;
    private final double y;

    public PlayerComponent(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public  void respawn() {
        entity.removeFromWorld();
        FXGL.spawn("player", new SpawnData(x, y));
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        texture.loopAnimationChannel(up);
    }

    public void left() {
        if (texture.getAnimationChannel() != left) {
            texture.loopAnimationChannel(left);
        }
    }

    public void right() {
        if (texture.getAnimationChannel() != right) {
            texture.loopAnimationChannel(right);
        }
    }

    public void up() {
        if (texture.getAnimationChannel() != up) {
            texture.loopAnimationChannel(up);
        }
    }

    public void down() {
        if (texture.getAnimationChannel() != down) {
            texture.loopAnimationChannel(down);
        }
    }
}
