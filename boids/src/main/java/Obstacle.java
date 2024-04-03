import java.awt.*;

public class Obstacle {
    private final int x, y, width = 20, height = 20;

    public Obstacle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vec getPositionVec() {
        return new Vec(x + (double) (width/2), y + (double) (height/2));
    }

    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(Color.RED);
        graphics2D.fillRect(x, y, width, height);
    }
}
