package bbrz.pacman.Pacman;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyDef;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PacmanFactory implements EntityFactory {
    @Spawns("player")
    public Entity spawnPlayer(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setFixtureDef(new FixtureDef().friction(0).density(0.1f));
        BodyDef bd = new BodyDef();
        bd.setFixedRotation(true);
        bd.setType(BodyType.DYNAMIC);
        physics.setBodyDef(bd);

        return FXGL.entityBuilder(data)
                .type(EntityType.PLAYER)
                .bbox(new HitBox(BoundingShape.box(17, 17)))
                .with(physics)
                .with(new PlayerComponent(data.getX(), data.getY()))
                .collidable()
                .build();
    }

    @Spawns("cherrySpawnPoint")
    public Entity spawnCherrySpawnPoint(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new CherrySpawnPointComponent())
                .build();
    }

    @Spawns("cherry")
    public Entity spawnCherry(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityType.CHERRY)
                .viewWithBBox("cherry.png")
                .collidable()
                .build();
    }

    @Spawns("coin")
    public Entity spawnCoin(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityType.COIN)
                .view("coin.png")
                .bbox(new HitBox("COIN_HIT_BOX", new Point2D(5, 5), BoundingShape.box(9, 9)))
                .collidable()
                .build();
    }

    @Spawns("wall")
    public Entity spawnWall(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityType.WALL)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .collidable()
                .build();
    }

    @Spawns("ghostwall")
    public Entity spawnGhostWall(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityType.GHOSTWALL)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .collidable()
                .build();
    }

    @Spawns("border")
    public Entity spawnBorder(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityType.BORDER)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .collidable()
                .build();
    }

    @Spawns("background")
    public Entity spawnBackground(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view(new Rectangle(data.<Integer>get("width"), data.<Integer>get("height"), Color.GRAY))
                .with(new IrremovableComponent())
                .zIndex(-100)
                .build();
    }

    @Spawns("ghost")
    public Entity spawnGhost(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityType.GHOST)
                .bbox(new HitBox(BoundingShape.box(17,17)))
                .with(new GhostComponent(data.get("name"), data.getX(), data.getY()))
                .collidable()
                .build();
    }

}
