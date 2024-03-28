import java.awt.*;
import java.awt.event.KeyEvent;

public class Followable {
    private int x = 100, y = 100;

    public Followable() {
    }

    public Vec getLocationVec() {
        return new Vec(x, y);
    }

    public void draw(Graphics2D graphics2D) {
        var save = graphics2D.getTransform();

        graphics2D.translate(x, y);
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(x, y, 20, 20);

        graphics2D.setTransform(save);
    }

    public void processPressedKey(KeyEvent pressedKey) {

        if (pressedKey.getKeyCode() == KeyEvent.VK_D) {
            x += 2;
        }

        if (pressedKey.getKeyCode() == KeyEvent.VK_A) {
            x -= 2;
        }

        if (pressedKey.getKeyCode() == KeyEvent.VK_W) {
            y -= 2;
        }

        if (pressedKey.getKeyCode() == KeyEvent.VK_S) {
            y += 2;
        }
    }
}