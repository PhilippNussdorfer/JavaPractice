package bbrz.adventure.game.ComputerEnemyBehavior;

import bbrz.adventure.game.Components.EnemyAnimationComponent;
import bbrz.adventure.game.EntityType;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class BehaviorComponent extends Component {
    private final double sightRadius;
    private final double minFollowingRadius;
    private final double separationDistance;

    /**
     * minRadius needs to be lower than sightRadius for the following to work.
     * @param target that gets followed;
     * @param followingEntity the entity to follow the target
     * @return returns a Vec with the x and y cords.
     */
    private Vec follow(Entity target, Entity followingEntity, EnemyAnimationComponent animationComponent) {
        Vec steer = new Vec();
        Vec targetPos = new Vec(target.getX(), target.getY());
        Vec followerPos = new Vec(followingEntity.getX(), followingEntity.getY());

        double distance = followingEntity.distance(target);
        if (distance < sightRadius && distance > minFollowingRadius) {
            Vec followDir = Vec.sub(targetPos, followerPos);
            followDir.normalize();

            Vec currentDir = getVelocityFromEntity(followingEntity);
            currentDir.normalize();
            followDir.sub(currentDir);
            followDir.normalize();

            int minMag = 150;

             if (followDir.mag() < minMag) {
                 double mag = followDir.mag();
                 double x = followDir.getX(), y = followDir.getY();

                 followDir = new Vec((x/mag) * minMag, (y/mag) * minMag);
                 followDir.normalize();
             }

            steer.add(followDir);
            animationComponent.move();
        } else {
            animationComponent.idle();
        }

        return steer;
    }

    private List<Entity> getEntityThatIsToClose(double distance, Entity enemy, Entity target, List<Entity> entityList) {
        List<Entity> entity_sToClose = new ArrayList<>();

        for (Entity entity : entityList) {
            if (entity != enemy && entity != target) {
                if (enemy.distanceBBox(entity) < distance && entity.getType() != EntityType.ITEM_BAG) {
                    entity_sToClose.add(entity);
                }
            }
        }
        return entity_sToClose;
    }

    private Vec separateFromOtherEntity_s(Entity enemy, List<Entity> obstaclesAndOtherEnemy_s) {
        Vec steer = new Vec();
        Vec enemyPos = new Vec(enemy.getPosition().getX(), enemy.getPosition().getY());

        for (Entity obstacle : obstaclesAndOtherEnemy_s) {
            Vec obstaclePos = new Vec(obstacle.getX(), obstacle.getY());

            double distance = enemy.distance(obstacle);
            if (distance < separationDistance) {
                Vec diff = Vec.sub(enemyPos, obstaclePos);
                diff.div(distance);
                diff.normalize();
                steer.add(diff);
            }
        }

        return steer;
    }

    public Vec2 follow(Entity target, EnemyAnimationComponent animationComponent, Entity enemy, int maxSpeed, List<Entity> allEntity_s) {
        List<Entity> entityList = getEntityThatIsToClose(separationDistance, enemy, target, allEntity_s);
        var acceleration = new Vec();

        var followRule = follow(target, enemy, animationComponent);
        var separationRule = separateFromOtherEntity_s(enemy, entityList);

        separationRule.multiply(3);
        followRule.multiply(2);

        acceleration.add(followRule);
        acceleration.add(separationRule);

        acceleration.normalize();
        acceleration.multiply(2);

        acceleration.limit(maxSpeed);

        return new Vec2(acceleration.getY(), acceleration.getX());
    }

    private Vec getVelocityFromEntity(Entity entity) {
        var physics = entity.getComponent(PhysicsComponent.class);
        return new Vec(physics.getVelocityX(), physics.getVelocityY());
    }
}
