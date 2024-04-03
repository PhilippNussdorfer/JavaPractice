package bbrz.adventure.game.ComputerEnemyBehavior;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.PhysicsComponent;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class Behavior {
    private final double sightRadius;
    private final double minFollowingRadius;

    /**
     * minRadius needs to be lower than sightRadius for the following to work.
     * @param target that gets followed;
     * @param followingEntity the entity to follow the target
     * @return returns a Vec with the x and y cords.
     */
    public Vec follow(Entity target, Entity followingEntity) {
        Vec steer = new Vec();
        Vec targetPos = new Vec(target.getX(), target.getY());
        Vec followerPos = new Vec(followingEntity.getX(), followingEntity.getY());

        double distance = Vec.dist(targetPos, followerPos);
        if (distance < sightRadius) {
            Vec avoidanceDir = Vec.sub(targetPos, followerPos);
            avoidanceDir.normalize();

            Vec currentDir = getVelocityFromEntity(followingEntity);
            currentDir.normalize();
            avoidanceDir.sub(currentDir);
            avoidanceDir.normalize();

            double avoidanceStrength = 1 - (distance/minFollowingRadius);
            avoidanceDir.multiply(avoidanceStrength);

            steer.add(avoidanceDir);
        }

        return steer;
    }

    public Vec separate(Entity enemy, List<Entity> obstaclesAndOtherEnemy_s) {
        Vec steer = new Vec();
        Vec enemyPos = new Vec(enemy.getPosition().getX(), enemy.getPosition().getY());

        for (Entity obstacle : obstaclesAndOtherEnemy_s) {
            Vec obstaclePos = new Vec(obstacle.getX(), obstacle.getY());

            double distance = Vec.dist(enemyPos, obstaclePos);
            if (distance < 45) {
                Vec diff = Vec.sub(enemyPos, obstaclePos);
                diff.normalize();
                diff.div(distance);
                steer.add(diff);
            }
        }

        return steer;
    }

    private Vec getVelocityFromEntity(Entity entity) {
        var physics = entity.getComponent(PhysicsComponent.class);
        return new Vec(physics.getVelocityX(), physics.getVelocityY());
    }
}
