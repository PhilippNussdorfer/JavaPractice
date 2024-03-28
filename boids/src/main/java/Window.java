// https://rosettacode.org/wiki/Boids/Java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Window extends JPanel {
    private Flock flock;
    private Flock secFlock;
    private Flock thirdFlock;
    private final int width = 1280, height = 840;
    private final PerlinNoise perlinNoise = new PerlinNoise();

    public Window() {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.cyan);

        perlinNoise.NoiseGenerator();
        System.out.println(perlinNoise.noise(4));
        System.out.println(perlinNoise.noise(4, 1005));

        spawnFlock();

        new Timer(17, (ActionEvent e) -> {
           /*if (flock.hasLeftTheWindow(width)) {
               flock.clearBoidsList();
               spawnFlock();
           }*/
            moveFlock();
            repaint();
        }).start();
    }

    private void spawnFlock() {
        flock = Flock.spawn(100, height * 0.5, 40, perlinNoise, width, height, Color.ORANGE);
        secFlock = Flock.spawn(600, 700, 50, perlinNoise, width, height, Color.BLUE);
        thirdFlock = Flock.spawn(1000, 150, 45, perlinNoise, width, height, Color.GREEN);
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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Boids");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.add(new Window(), BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}