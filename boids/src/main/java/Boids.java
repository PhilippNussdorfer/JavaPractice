import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.List;
import java.util.Random;

public class Boids {
    private final Vec migrate = new Vec(0.02, 0);
    private final double separationDistance;
    private final double alignmentDistance;
    private final double cohesionDistance;
    @Getter
    static final int SIZE = 3;
    static final Path2D SHAPE = new Path2D.Double();
    private final Color boidsColor;

    static  {
        SHAPE.moveTo(0, -SIZE * 2);
        SHAPE.lineTo(-SIZE, SIZE * 2);
        SHAPE.lineTo(SIZE, SIZE * 2);
        SHAPE.closePath();
    }

    private final double maxForce, maxSpeed;
    @Getter
    @Setter
    private Vec location, velocity, acceleration;
    private boolean included = true;
    private double x = 0, y = 1000;
    private boolean xCountUp = true, yCountUp = true;
    private final PerlinNoise perlinNoise;
    private final Followable followable;

    public Boids(double x, double y, double separationDistance, double alignmentDistance, double cohesionDistance, PerlinNoise perlinNoise, Followable followable, Color boidsColor) {
        Random random = new Random();

        this.acceleration = new Vec();
        this.velocity = new Vec(random.nextInt(3) + 1, random.nextInt(3) - 1);
        this.location = new Vec(x, y);

        this.maxSpeed = 3;
        this.maxForce = 0.05;

        this.separationDistance = separationDistance; //25
        this.alignmentDistance = alignmentDistance;   //50
        this.cohesionDistance = cohesionDistance;     //50

        this.boidsColor = boidsColor;
        this.perlinNoise = perlinNoise;
        this.followable = followable;

        perlinNoise.NoiseGenerator();
    }

    private double getXNoise() {
        double noise = perlinNoise.noise(x);

        if (xCountUp) {
            x += 0.01;
            if (x >= 1000) {
                xCountUp = false;
            }
        }

        if (!xCountUp) {
            x -= 0.01;
            if (x <= 0) {
                xCountUp = true;
            }
        }

        return noise;
    }

    private double getYNoise() {
        double noise = perlinNoise.noise(y);

        if (yCountUp) {
            y += 0.01;
            if (y >= 2000) {
                yCountUp = false;
            }
        }

        if (!yCountUp) {
            y -= 0.01;
            if (y <= 1000) {
                yCountUp = true;
            }
        }

        return noise;
    }

    private Vec getNoiseVec() {
        return new Vec(getXNoise(), getYNoise());
    }

    private void update() {
        velocity.add(acceleration);
        velocity.limit(maxSpeed);
        location.add(velocity);
        acceleration.multiply(0);
    }

    private void applyForce(Vec force) {
        acceleration.add(force);
    }

    private Vec seek(Vec target) {
        Vec steer = Vec.sub(target, location);
        steer.normalize();
        steer.multiply(maxSpeed);
        steer.sub(velocity);
        steer.limit(maxForce);
        return steer;
    }

    private void view(List<Boids> boidsList) {
        double sightDistance = 100;
        double peripheryAngle = Math.PI * 0.85;

        for (Boids boids : boidsList) {
            boids.included = false;
            if (boids == this)
                continue;

            double distance = Vec.dist(location, boids.location);
            if (distance <= 0 || distance > sightDistance)
                continue;

            Vec lineOfSight = Vec.sub(boids.location, location);
            double angle = Vec.angleBetween(lineOfSight, velocity);
            if (angle < peripheryAngle) {
                boids.included = true;
            }
        }
    }

    private void flock(List<Boids> boidsList) {
        view(boidsList);

        Vec rule1 = separation(boidsList);
        Vec rule2 = alignment(boidsList);
        Vec rule3 = cohesion(boidsList);

        rule1.multiply(2.5);    //2.5
        rule2.multiply(1.5);    //1.5
        rule3.multiply(1.9);    //1.3

        applyForce(rule1);
        applyForce(rule2);
        applyForce(rule3);
        applyForce(migrate);
    }

    private Vec separation(List<Boids> boidsList) {
        int count = 0;
        Vec steer = new Vec(0, 0);

        for (Boids boids : boidsList) {
            if (!boids.included)
                continue;

            double distance = Vec.dist(location, boids.location);
            if (distance > 0 && distance < separationDistance) {
                Vec diff = Vec.sub(location, boids.location);
                diff.normalize();
                diff.div(distance);
                steer.add(diff);
                count++;
            }
        }

        if (count > 0) {
            steer.div(count);
        }

        if (steer.mag() > 0) {
            steer.normalize();
            steer.multiply(maxSpeed);
            steer.sub(velocity);
            steer.limit(maxForce);
            return steer;
        }
        return new Vec(0, 0);
    }

    private Vec alignment(List<Boids> boidsList) {
        int count = 0;
        Vec steer = new Vec(0, 0);

        for (Boids boids : boidsList) {
            if (!boids.included) {
                steer.add(getNoiseVec());
                steer.normalize();
                continue;
            }

            double distance = Vec.dist(location, boids.location);
            if (distance > 0 && distance < alignmentDistance) {
                steer.add(boids.velocity);
                count ++;
            }

            if (count > 0) {
                steer.div(count);
                steer.normalize();
                steer.multiply(maxSpeed);
                steer.sub(velocity);
                steer.limit(maxForce);
            }
        }

        return steer;
    }

    private Vec cohesion(List<Boids> boidsList) {
        int count = 0;
        Vec target = new Vec(0, 0);

        for (Boids boids : boidsList) {
            if (!boids.included)
                continue;

            double distance = Vec.dist(location, boids.location);
            if (distance > 0 && distance < cohesionDistance) {
                target.add(boids.location);
                count++;
            }
        }

        if (count > 0) {
            target.div(count);
            return seek(target);
        }

        return target;
    }

    private void draw(Graphics2D graphics2D) {
        AffineTransform save = graphics2D.getTransform();

        graphics2D.translate(location.getX(), location.getY());
        graphics2D.rotate(velocity.heading() + Math.PI / 2);
        graphics2D.setColor(boidsColor);
        graphics2D.fill(SHAPE);
        graphics2D.setColor(Color.BLACK);
        graphics2D.draw(SHAPE);

        graphics2D.setTransform(save);
    }

    public void run(Graphics2D graphics2D, List<Boids> boidsList) {
        flock(boidsList);
        update();
        draw(graphics2D);
    }
}
