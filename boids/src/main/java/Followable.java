import java.awt.*;
import java.awt.event.KeyEvent;

public class Followable {
    private int x = 100, y = 100;
    private final int width = 20, height = 20;

    public Followable() {
    }

    public Vec getLocationVec() {
        return new Vec(x + (double) (width/2), y + (double) (height/2));
    }

    public void draw(Graphics2D graphics2D) {
        var save = graphics2D.getTransform();

        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(x, y, width, height);

        graphics2D.setTransform(save);
    }

    public void processPressedKey(KeyEvent pressedKey) {

        if (pressedKey.getKeyCode() == KeyEvent.VK_D) {
            x += 5;
        }

        if (pressedKey.getKeyCode() == KeyEvent.VK_A) {
            x -= 5;
        }

        if (pressedKey.getKeyCode() == KeyEvent.VK_W) {
            y -= 5;
        }

        if (pressedKey.getKeyCode() == KeyEvent.VK_S) {
            y += 5;
        }

        System.out.println("X: " + x + " Y: " + y);
    }
}