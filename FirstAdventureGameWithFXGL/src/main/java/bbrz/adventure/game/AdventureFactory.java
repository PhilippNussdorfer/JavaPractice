package bbrz.adventure.game;

import bbrz.adventure.game.Components.*;
import bbrz.adventure.game.ComputerEnemyBehavior.BehaviorComponent;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyDef;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class AdventureFactory implements EntityFactory {

    @Spawns("player")
    public Entity spawnPlayer(SpawnData data) {
        PhysicsComponent physics = getPhysicsComponent();
        return entityBuilder(data)
                .type(EntityType.PLAYER)
                .bbox(new HitBox(BoundingShape.box(18, 26)))
                .with(physics)
                .with(new PlayerAnimationComponent(data.getX(), data.getY()))
                .with(new PlayerComponent())
                .collidable()
                .build();
    }

    @Spawns("skeleton")
    public Entity spawnSkeleton(SpawnData data) {
        PhysicsComponent physics = getPhysicsComponent();

        return entityBuilder(data)
                .type(EntityType.ENEMY)
                .bbox(new HitBox(BoundingShape.box(20, 30)))
                .with(physics)
                .with(new EnemyComponent(data.getX(), data.getY(), 1, 5, 15, EnemyIndicator.SKELETON))
                .with(new EnemyAnimationComponent("skeleton"))
                .with(new BehaviorComponent(200, 60, 60))
                .collidable()
                .build();
    }

    @Spawns("itembag")
    public Entity itemBag(SpawnData data) {
        Texture itemBag = FXGL.getAssetLoader().loadTexture("items/ItemBag.png");
        itemBag.setTranslateX(20);
        itemBag.setTranslateY(20);

        return entityBuilder(data)
                .type(EntityType.ITEM_BAG)
                .view(itemBag)
                .bbox(new HitBox(BoundingShape.box(50, 50)))
                .with(new ItemBagComponent())
                .build();
    }

    @Spawns("bounds")
    public Entity spawnsBounds(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.BOUNDS)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .collidable()
                .build();
    }

    @Spawns("player-close-combat-area")
    public Entity spawnsArea(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.PLAYER_COMBAT_AREA)
                .viewWithBBox(new Rectangle(65, 26, Color.TRANSPARENT))
                .collidable()
                .with(new CombatComponent())
                .at(getGameWorld().getSingleton(EntityType.PLAYER).getCenter())
                .build();
    }

    @Spawns("player-arrow")
    public Entity spawnsPlayerProjectile(SpawnData data) {
        Point2D direction = getInput().getMousePositionWorld().subtract(getGameWorld().getSingleton(EntityType.PLAYER).getCenter());
        Texture arrow = getAssetLoader().loadTexture("bow/Arrow.png");
        arrow.getTransforms().add(new Rotate(90));
        return entityBuilder(data)
                .type(EntityType.PLAYER_PROJECTILE)
                .view(arrow)
                .bbox(new HitBox(BoundingShape.box(5, 2)))
                .collidable()
                .with(new ProjectileComponent(direction, 450))
                .with(new OffscreenCleanComponent())
                .at(getGameWorld().getSingleton(EntityType.PLAYER).getCenter())
                .build();
    }

    @Spawns("enemy-arrow")
    public Entity spawnsEnemyProjectile(SpawnData data) {
        Point2D direction = getGameWorld().getSingleton(EntityType.PLAYER).getCenter();
        Texture arrow = getAssetLoader().loadTexture("bow/Arrow.png");
        arrow.getTransforms().add(new Rotate(90));
        return entityBuilder(data)
                .type(EntityType.PLAYER_PROJECTILE)
                .view(arrow)
                .bbox(new HitBox(BoundingShape.box(5, 2)))
                .collidable()
                .with(new ProjectileComponent(direction, 450))
                .with(new OffscreenCleanComponent())
                .at(getGameWorld().getSingleton(EntityType.PLAYER).getCenter())
                .build();
    }

    private static PhysicsComponent getPhysicsComponent() {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setFixtureDef(new FixtureDef().friction(0f).density(3.1f));
        BodyDef bd = new BodyDef();
        bd.setFixedRotation(true);
        bd.setType(BodyType.DYNAMIC);
        physics.setBodyDef(bd);
        return physics;
    }
}
