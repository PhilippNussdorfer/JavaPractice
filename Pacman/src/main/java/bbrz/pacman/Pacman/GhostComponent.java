package bbrz.pacman.Pacman;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;

import java.util.Random;

public class GhostComponent extends Component {
    private final String name;
    private final double x;
    private final double y;

    private final Texture left;
    private final Texture right;
    private final Texture down;
    private final Texture up;

    private static final double SPEED = 140;
    private static final Random RANDOM = new Random();

    private double dx = 0.0;
    private double dy = -SPEED;

    public GhostComponent(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;

        left = FXGL.texture(name +"-left.png");
        right = FXGL.texture(name +"-right.png");
        down = FXGL.texture(name +"-down.png");
        up = FXGL.texture(name +"-up.png");
    }

    public void turn() {
        if (dx < 0.0) {
            entity.translateX(2);
            dx = 0.0;
            dy = getRandomSpeedAndDirection();
        } else if (dx > 0.0) {
            entity.translateX(-2);
            dx = 0.0;
            dy = getRandomSpeedAndDirection();
        } else if (dy < 0.0) {
            entity.translateY(2);
            dy = 0.0;
            dx = getRandomSpeedAndDirection();
        } else {
            entity.translateY(-2);
            dy = 0.0;
            dx = getRandomSpeedAndDirection();
        }

        if (dx < 0.0) {
            entity.getViewComponent().removeChild(down);
            entity.getViewComponent().removeChild(right);
            entity.getViewComponent().removeChild(up);
            entity.getViewComponent().addChild(left);
        } else if (dx > 0.0) {
            entity.getViewComponent().removeChild(up);
            entity.getViewComponent().removeChild(left);
            entity.getViewComponent().removeChild(down);
            entity.getViewComponent().addChild(right);
        } else if (dy > 0.0) {
            entity.getViewComponent().removeChild(left);
            entity.getViewComponent().removeChild(right);
            entity.getViewComponent().removeChild(up);
            entity.getViewComponent().addChild(down);
        } else {
            entity.getViewComponent().removeChild(down);
            entity.getViewComponent().removeChild(right);
            entity.getViewComponent().removeChild(left);
            entity.getViewComponent().addChild(up);
        }
    }

    public void respawn() {
        entity.removeFromWorld();
        FXGL.spawn("ghost", new SpawnData(x, y).put("name", name));
    }

    private double getRandomSpeedAndDirection() {
        return RANDOM.nextBoolean() ? SPEED : -SPEED;
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(down);
    }

    @Override
    public void onUpdate(double tpf) {  // tpf -> Time per Frame
        entity.translateX(dx * tpf);
        entity.translateY(dy * tpf);
    }
}
