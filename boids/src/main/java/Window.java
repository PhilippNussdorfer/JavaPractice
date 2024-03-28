// https://rosettacode.org/wiki/Boids/Java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Window extends JPanel implements KeyListener {
    private Flock flock;
    private Flock secFlock;
    private Flock thirdFlock;
    private final int width = 1280, height = 840;
    private final Followable followable = new Followable();

    public Window() {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.cyan);

        spawnFlock();

        new Timer(17, (ActionEvent e) -> {
            moveFlock();
            repaint();
        }).start();
    }

    private void spawnFlock() {
        flock = Flock.spawn(100, height * 0.5, 40, followable, width, height, Color.ORANGE);
        secFlock = Flock.spawn(600, 700, 50, followable, width, height, Color.BLUE);
        thirdFlock = Flock.spawn(1000, 150, 45, followable, width, height, Color.GREEN);
    }

    private void moveFlock() {
        flock.moveBoids();
        secFlock.moveBoids();
        thirdFlock.moveBoids();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        flock.run(graphics2D);
        secFlock.run(graphics2D);
        thirdFlock.run(graphics2D);
        followable.draw(graphics2D);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Window window = new Window();
            JFrame frame = new JFrame("Boids");

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.add(window, BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.addKeyListener(window);
            frame.setVisible(true);
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        followable.processPressedKey(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}