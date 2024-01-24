package bbrz.pacman.Pacman;

import com.almasb.fxgl.achievement.Achievement;
import com.almasb.fxgl.achievement.AchievementEvent;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.PhysicsWorld;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

public class PacmanGame extends GameApplication {

    private static final int SPEED = 150;
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static int startLvl = 1;

    public static void main(String[] args) {
        if (args.length > 0) {
            startLvl = Integer.parseInt(args[0]);
        }
        launch(args);
    }

    public static Entity getPlayer() {
        return getGameWorld().getSingleton(EntityType.PLAYER);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("Level", startLvl);
        vars.put("Score", 0);
        vars.put("Lives", 3);
        vars.put("eatenCoin", 0);
        vars.put("deaths", 0);
        vars.put("cherrys", 0);
    }

    @Override
    protected void initUI() {
        Label scoreLabel = new Label();
        scoreLabel.setTextFill(Color.YELLOW);
        scoreLabel.setFont(Font.font(20.0));
        scoreLabel.textProperty().bind(FXGL.getip("Score").asString("Score: %d"));
        addUINode(scoreLabel, 20, 10);

        Label levelLabel = new Label();
        levelLabel.setTextFill(Color.BLACK);
        levelLabel.setFont(Font.font(20.0));
        levelLabel.textProperty().bind(FXGL.getip("Level").asString("Level: %d"));
        FXGL.addUINode(levelLabel, WIDTH - 100, 10);

        Label liveLabel = new Label();
        liveLabel.setTextFill(Color.RED);
        liveLabel.setFont(Font.font(20.0));
        liveLabel.textProperty().bind(FXGL.getip("Lives").asString("Lives: %d"));
        FXGL.addUINode(liveLabel, (int) WIDTH/2, 10);
    }

    @Override
    protected void initPhysics() {
        PhysicsWorld physics = getPhysicsWorld();
        physics.setGravity(0, 0);
        physics.addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.COIN) {
            @Override
            protected void onCollisionBegin(Entity player, Entity coin) {
                FXGL.play("pill.wav");
                coin.removeFromWorld();
                FXGL.inc("Score", 7);
                FXGL.inc("coins", -1);
                FXGL.inc("eatenCoin", 1);
                if (FXGL.geti("coins") == 0) {
                    FXGL.inc("Level", 1);
                    FXGL.play("level.wav");
                    Platform.runLater(() -> initLevel());
                }
            }
        });

        physics.addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.CHERRY) {
            @Override
            protected void onCollisionBegin(Entity player, Entity cherry) {
                FXGL.play("cherry.wav");
                cherry.removeFromWorld();
                FXGL.inc("Score", 15);
                FXGL.inc("cherrys", 1);
            }
        });

        physics.addCollisionHandler(new CollisionHandler(EntityType.GHOST, EntityType.WALL) {
            @Override
            protected void onCollisionBegin(Entity ghost, Entity wall) {
                ghost.getComponent(GhostComponent.class).turn();
            }
        });

        physics.addCollisionHandler(new CollisionHandler(EntityType.GHOST, EntityType.BORDER) {
            @Override
            protected void onCollisionBegin(Entity ghost, Entity border) {
                ghost.getComponent(GhostComponent.class).respawn();
            }
        });

        physics.addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.GHOST) {
            @Override
            protected void onCollisionBegin(Entity player, Entity ghost) {
                FXGL.play("death.wav");
                FXGL.inc("Lives", -1);
                FXGL.inc("deaths", 1);
                if (FXGL.geti("Lives") > 0) {
                    FXGL.getGameWorld()
                            .getEntitiesByType(EntityType.GHOST)
                            .forEach(entity -> entity.getComponent(GhostComponent.class).respawn());
                    player.getComponent(PlayerComponent.class).respawn();
                    FXGL.inc("Score", -35);
                } else {
                    gameOver(false);
                }
            }
        });
    }

    private void gameOver(boolean reachedEndOfGame) {
        StringBuilder builder = new StringBuilder();
        builder.append("Game Over!\n\n");
        if (reachedEndOfGame) {
            builder.append("You have reached the end of the game!\n\n");
        }
        builder.append("Final score: ")
                .append(FXGL.geti("Score"))
                .append("\nFinal level: ")
                .append(FXGL.geti("Level") - 1);
        FXGL.getDialogService().showMessageBox(builder.toString(), () -> FXGL.getGameController().gotoMainMenu());
    }

    @Override
    protected void initInput() {
        Input input = getInput();
        input.addAction(goUp, KeyCode.W);
        input.addAction(goDown, KeyCode.S);
        input.addAction(goLeft, KeyCode.A);
        input.addAction(goRight, KeyCode.D);
    }

    UserAction goDown = new UserAction("Move Down") {
        @Override
        protected void onAction() {
            getPlayer().getComponent(PhysicsComponent.class).setVelocityY(SPEED);
            getPlayer().getComponent(PlayerComponent.class).down();
        }
        @Override
        protected void onActionEnd() {
            getPlayer().getComponent(PhysicsComponent.class).setVelocityY(0);
        }
    };

    UserAction goRight = new UserAction("Move Right") {
        @Override
        protected void onAction() {
            getPlayer().getComponent(PhysicsComponent.class).setVelocityX(SPEED);
            getPlayer().getComponent(PlayerComponent.class).right();
        }
        @Override
        protected void onActionEnd() {
            getPlayer().getComponent(PhysicsComponent.class).setVelocityX(0);
        }
    };

    UserAction goLeft = new UserAction("Move Left") {
        @Override
        protected void onAction() {
            getPlayer().getComponent(PhysicsComponent.class).setVelocityX(-SPEED);
            getPlayer().getComponent(PlayerComponent.class).left();
        }
        @Override
        protected void onActionEnd() {
            getPlayer().getComponent(PhysicsComponent.class).setVelocityX(0);
        }
    };

    UserAction goUp = new UserAction("Move Up") {
        @Override
        protected void onAction() {
            getPlayer().getComponent(PhysicsComponent.class).setVelocityY(-SPEED);
            getPlayer().getComponent(PlayerComponent.class).up();
        }
        @Override
        protected void onActionEnd() {
            getPlayer().getComponent(PhysicsComponent.class).setVelocityY(0);
        }
    };

    @Override
    protected void initGame() {
            FXGL.getGameWorld().addEntityFactory(new PacmanFactory());
            initLevel();
            getEventBus().addEventHandler(AchievementEvent.ANY, e -> {
                getNotificationService().pushNotification(e.getAchievement().getName());
            });
    }

    protected void initLevel() {
        FXGL.spawn("background", new SpawnData(0, 0).put("width", WIDTH).put("height", HEIGHT));
        setLvlFromMapOrGameOver();
        FXGL.set("coins", FXGL.getGameWorld().getEntitiesByType(EntityType.COIN).size());
    }

    private void setLvlFromMapOrGameOver() {
        try {
            FXGL.setLevelFromMap("lvl_" + FXGL.geti("Level") + ".tmx");
        } catch (IllegalArgumentException e) {
            gameOver(true);
        }
    }

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.getAchievements().add(new Achievement("My First Coin", "Eat your first Coin", "eatenCoin", 1));
        gameSettings.getAchievements().add(new Achievement("Belly full of Coins", "Eat one hundred coins", "eatenCoin", 100));
        gameSettings.getAchievements().add(new Achievement("Eaten by blinky", "get eaten by an Ghost", "deaths", 1));
        gameSettings.getAchievements().add(new Achievement("It's time to get the White flag out", "get eaten 3 times by a ghost", "deaths", 3));
        gameSettings.getAchievements().add(new Achievement("My First Cherry", "Eat your first Cherry", "cherrys", 1));

        gameSettings.setMainMenuEnabled(true);
        gameSettings.setFullScreenAllowed(true);
        gameSettings.setManualResizeEnabled(true);

        gameSettings.setWidth(WIDTH);
        gameSettings.setHeight(HEIGHT);

        gameSettings.setTitle("Pacman");
        gameSettings.setVersion("0.2.3");
    }
}
