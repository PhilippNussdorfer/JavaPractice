import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Flock {
    List<Boids> boidsList;
    private final int width, height;

    public Flock(int width, int height) {
        boidsList = new ArrayList<>();
        this.width = width;
        this.height = height;
    }

    public void run(Graphics2D graphics2D) {
        for (Boids boids : boidsList) {
            boids.run(graphics2D, boidsList);
        }
    }

    public void moveBoids() {
        for (Boids boids : boidsList) {
            double boidsSize = Boids.getSIZE();
            if (boids.getLocation().getX() + boidsSize >= width) {
                boids.setLocation(new Vec(4, boids.getLocation().getY()));
            }

            if (boids.getLocation().getX() - boidsSize <= 0) {
                boids.setLocation(new Vec(width - 4, boids.getLocation().getY()));
            }

            if (boids.getLocation().getY() + boidsSize >= height) {
                boids.setLocation(new Vec(boids.getLocation().getX(), 4));
            }

            if (boids.getLocation().getY() - boidsSize <= 0) {
                boids.setLocation(new Vec(boids.getLocation().getX(), height - 4));
            }
        }
    }

    public void addBoids(Boids boids) {
        boidsList.add(boids);
    }

    public static Flock spawn(int x, double y, int amount, PerlinNoise perlinNoise, int width, int height, Color flockColor) {
        Flock flock = new Flock(width, height);
        for (int i = 0; i < amount; i++) {
            flock.addBoids(new Boids(x, y, 25, 50, 75, perlinNoise, flockColor));
        }
        return flock;
    }
}
