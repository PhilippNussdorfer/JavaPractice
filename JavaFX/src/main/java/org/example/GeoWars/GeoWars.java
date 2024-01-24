package org.example.GeoWars;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;

import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.time.TimerAction;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

public class GeoWars extends GameApplication {

    private final GeoWarsFactory geoWarsFactory = new GeoWarsFactory();
    private Entity player;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    protected void initInput() {
        onKey(KeyCode.W, ()->player.translateY(-5));
        onKey(KeyCode.S, ()->player.translateY(5));
        onKey(KeyCode.A, ()->player.translateX(-5));
        onKey(KeyCode.D, ()->player.translateX(5));
        onBtn(MouseButton.PRIMARY, ()->spawn("bullet", player.getCenter()));
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(geoWarsFactory);

        this.player = spawn("player", getAppWidth()/2 - 15, getAppHeight()/2 - 15);

        run(()->spawn("enemy"), Duration.seconds(0.5));
    }

    @Override
    protected void initPhysics() {
        onCollisionBegin(EntityType.BULLET, EntityType.ENEMY, (bullet, enemy)->{
            bullet.removeFromWorld();
            enemy.removeFromWorld();
        });

        onCollisionBegin(EntityType.ENEMY, EntityType.PLAYER, (enemy, player) ->{
            showMessage("You Died!", ()->{
                getGameController().startNewGame();
            });
        });
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Geo Wars");
    }

    public enum EntityType {
        PLAYER,
        BULLET,
        ENEMY
    }
}