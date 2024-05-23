package bbrz.adventure.game;

import bbrz.adventure.game.AnimationAreaDirection.*;
import bbrz.adventure.game.Components.*;
import bbrz.adventure.game.InputRegister.Action;
import bbrz.adventure.game.InputRegister.Direction;
import bbrz.adventure.game.InputRegister.InputStatus;
import bbrz.adventure.game.Inventory.InventoryView;
import com.almasb.fxgl.app.CursorInfo;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.MenuItem;
import com.almasb.fxgl.core.serialization.Bundle;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.profile.DataFile;
import com.almasb.fxgl.profile.SaveLoadHandler;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.*;
import java.util.function.Predicate;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class MamblsAdventure extends GameApplication {
    private static final int width = 1280, height = 720;
    private static final String gameMap = "begin.tmx";
    private List<Texture> oldTextures;
    private boolean isInventoryOpen = false;
    private static final InputStream fontPathWithGetClass = MamblsAdventure.class.getClassLoader().getResourceAsStream("assets/ui/fonts/font/MinimalPixelv2.ttf");
    public static final Font gameFont = Font.loadFont(fontPathWithGetClass, 20);
    private static PlayerComponent playerComponent;
    private final InputStatus inputStatus = new InputStatus();
    public static final Predicate<Entity> isEntityType = entity -> (entity.getType() == EntityType.ITEM_BAG);

    public static void main(String[] args) {
        launch(args);
    }

    private static Entity getPlayer() {
        return getGameWorld().getSingleton(EntityType.PLAYER);
    }

    @Override
    protected void initGameVars(Map<String, Object> var) {
        var.put("currency", 0);
        var.put("isRunning", 0);
        var.put("runningActive", 0);
        var.put("time", 0);
    }

    @Override
    protected void onPreInit() {
        getSaveLoadService().addHandler(new SaveLoadHandler() {
            @Override
            public void onSave(@NotNull DataFile dataFile) {
                var bundle = new Bundle("gameData");
                double time = geti("time");
                bundle.put("time", time);

                dataFile.putBundle(bundle);
            }

            @Override
            public void onLoad(@NotNull DataFile dataFile) {
                var bundle = dataFile.getBundle("time");
                double time = bundle.get("time");
                set("time", time);
            }
        });
    }

    @Override
    protected void initUI() {
        Texture background = getTexture("gui/background_with_moneycount.png", 0, FXGL.getAppHeight() - 90, 90, 220);
        Texture playerHead = getTexture("gui/PlayerHead.png", 18, FXGL.getAppHeight() -68, 50, 50);

        FXGL.addUINode(background);
        FXGL.addUINode(playerHead);
        FXGL.addUINode(playerComponent.getInventoryview());

        addLabelToUI(114, FXGL.getAppHeight() - 31, "currency", Color.WHITE, 1);
    }

    private List<Texture> getUpdatableTexturesList() {
        int maxHealth = (int) playerComponent.getMaxHp();
        int maxStamina = playerComponent.getMaxStamina();
        int stamina = playerComponent.getStamina();
        int health = playerComponent.getHp();

        Texture secStaminaBar = getTexture("gui/secStaminaBar.png", 89, getAppHeight() - 58, 10, getBarWidth(140, maxStamina, stamina));
        Texture staminaBar = getTexture("gui/staminaBar.png", 89, getAppHeight() - 58, 10, getBarWidth(135, maxStamina, stamina));
        Texture staminaBarBorder = getTexture("gui/stamina_bar.png", 89, getAppHeight() - 60, 20, 140);

        Texture secHealthBar = getTexture("gui/secHealthBar.png", 89, getAppHeight() - 78, 10, getBarWidth(160, maxHealth, health));
        Texture healthBar = getTexture("gui/healthBar.png", 89, getAppHeight() - 78, 10, getBarWidth(155, maxHealth, health));
        Texture healthBarBorder = getTexture("gui/health_bar.png", 89, getAppHeight() - 80, 20, 160);

        return List.of(secHealthBar, healthBar, healthBarBorder, secStaminaBar, staminaBar, staminaBarBorder);
    }

    @Override
    public void onUpdate(double dbf) {
        List<Texture> gotTextures = getUpdatableTexturesList();

        if (oldTextures != null) {
            for (Texture texture : oldTextures) {
                FXGL.removeUINode(texture);
            }
        }

        for (Texture texture : gotTextures) {
            FXGL.addUINode(texture);
        }
        oldTextures = gotTextures;

        if (FXGL.geti("isRunning") == 0 && playerComponent.getStamina() < 300) {
            playerComponent.incStamina(1);
        }

        if (FXGL.geti("isRunning") == 0 && playerComponent.getPlayerMoveSpeed() > 150) {
            playerComponent.incPlayerMoveSpeed(-2);
        }
    }

    private Texture getTexture(String image, int x, int y, int height, int width) {
        var texture = FXGL.getAssetLoader().loadTexture(image);
        texture.setTranslateX(x);
        texture.setTranslateY(y);
        texture.setFitHeight(height);
        texture.setFitWidth(width);
        return texture;
    }

    private void addLabelToUI(int x, int y, String str, Color color, double opacity) {
        Label label = new Label();
        label.setTextFill(color);
        label.setFont(gameFont);
        label.textProperty().bind(FXGL.getip(str).asString("%d"));
        label.setOpacity(opacity);
        FXGL.addUINode(label, x, y);
    }

    private void addFadedLabelToUiAndRemoveAfterTime(Point2D point2D, String fadingStr, Color color, double duration) {
        Label label = new Label(fadingStr);
        label.setFont(gameFont);
        label.setOpacity(0.7);
        label.setTextFill(color);

        Random rnd = new Random();

        FXGL.addUINode(label, (point2D.getX() + 180) + rnd.nextInt(60), (point2D.getY() - 70) + rnd.nextInt(60));
        getGameTimer().runOnceAfter(() -> FXGL.removeUINode(label), Duration.seconds(duration));
    }

    private int getBarWidth(int maxLength, int maxBar, int actualBar) {
        if (actualBar <= 0) {
            return 1;
        }
        int proCent = (actualBar * 100) / maxBar;
        double modifier = (double) proCent / 100;
        return (int) (maxLength * modifier);
    }

    @Override
    protected void initPhysics() {
        PhysicsWorld physics = getPhysicsWorld();
        physics.setGravity(0,0);

        onCollisionBegin(EntityType.ENEMY, EntityType.PLAYER_PROJECTILE, (enemy, arrow) -> {
            arrow.removeFromWorld();
            var dmg = playerComponent.getDmg();
            addFadedLabelToUiAndRemoveAfterTime(enemy.getPosition(), "" + dmg, getColor(dmg), 0.7);
            enemy
                    .getComponent(EnemyAnimationComponent.class)
                    .checkIfDead(enemy
                            .getComponent(EnemyComponent.class)
                            .incHp(dmg));
            return null;
        });

        onCollisionBegin(EntityType.ENEMY, EntityType.PLAYER_COMBAT_AREA, (enemy, slash) -> {
            var dmg = playerComponent.getDmg();
            addFadedLabelToUiAndRemoveAfterTime(enemy.getPosition(), "" + dmg, getColor(dmg), 0.5);
            enemy
                    .getComponent(EnemyAnimationComponent.class)
                    .checkIfDead(enemy
                            .getComponent(EnemyComponent.class)
                            .incHp(dmg));
            return null;
        });
    }

    private static Color getColor(int dmg) {
        if (dmg * -1 == playerComponent.getMaxDmg()) {
            return Color.RED;
        } else if (dmg * -1 == playerComponent.getMinDmg()) {
            return Color.WHITE;
        } else {
            return Color.YELLOW;
        }
    }

    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new AdventureFactory());
        FXGL.setLevelFromMap(gameMap);

        getGameScene().getViewport().setZoom(2);
        getGameScene().getViewport().setBounds(0,0, FXGL.setLevelFromMap(gameMap).getWidth(), FXGL.setLevelFromMap(gameMap).getHeight());
        getGameScene().getViewport().bindToEntity(getPlayer(), ((double) (getAppWidth()/2)-20), (double) ((getAppHeight()/2))-20);

        playerComponent = getPlayer().getComponent(PlayerComponent.class);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setMainMenuEnabled(true);
        settings.setFullScreenAllowed(true);
        settings.setManualResizeEnabled(true);
        settings.setCloseConfirmation(true);
        settings.setDeveloperMenuEnabled(true);

        settings.setFontGame("font/MinimalPixelv2.ttf");
        settings.setFontText("font/MinimalPixelv2.ttf");
        settings.setFontUI("font/MinimalPixelv2.ttf");
        settings.setFontMono("font/MinimalPixelv2.ttf");

        settings.setWidth(width);
        settings.setHeight(height);

        settings.setTitle("Mambel's Adventure");
        settings.setVersion("0.0.1");
        settings.setAppIcon("gui/PlayerHead.png");
        settings.setDefaultCursor(new CursorInfo("Cursor Icons/a- Light + Black_Color/1 cursor.png", 10, 10));
        settings.setEnabledMenuItems(EnumSet.allOf(MenuItem.class));
    }

    @Override
    protected void initInput() {
        Input input = getInput();
        input.addAction(goUp, KeyCode.W);
        input.addAction(goDown, KeyCode.S);
        input.addAction(goLeft, KeyCode.A);
        input.addAction(goRight, KeyCode.D);
        input.addAction(attack, MouseButton.PRIMARY);
        input.addAction(run, KeyCode.SPACE);
        input.addAction(showInventoryOrClose, KeyCode.E);
        input.addAction(interact, KeyCode.F);
    }

    UserAction goDown  = new Action("Move Down", this.inputStatus, Direction.DOWN, false, true);
    UserAction goUp = new Action("Move Up", this.inputStatus, Direction.UP, false, false);
    UserAction goLeft = new Action("Move Left", this.inputStatus, Direction.LEFT, true, false);
    UserAction goRight = new Action("Move right", this.inputStatus, Direction.RIGHT, true, true);

    UserAction showInventoryOrClose = new UserAction("Toggle Inventory") {
        @Override
        protected void onActionBegin() {
            isInventoryOpen = showInventory(isInventoryOpen, playerComponent.getInventoryview());
        }
    };

    UserAction run = new UserAction("Run") {

        @Override
        protected void onActionBegin() {
            FXGL.inc("runningActive", 1);
        }

        @Override
        protected void onAction() {
            playerComponent.calculateRunning();
        }

        @Override
        protected void onActionEnd() {
            FXGL.inc("runningActive", -1);
        }
    };

    UserAction interact = new UserAction("Interact") {
        @Override
        protected void onActionBegin() {
            if (existsEntity(EntityType.ITEM_BAG)) {
                var i = getGameWorld().getClosestEntity(getPlayer(), isEntityType);
                i.ifPresent(entity -> entity.getComponent(ItemBagComponent.class).interact());
            }
        }
    };

    public static boolean existsEntity(EntityType entityType) {
        try {
            if (getPlayer().isColliding(getGameWorld().getSingleton(entityType))) {
                return true;
            }
            return true;
        } catch (NoSuchElementException i) {
            return false;
        }
    }

    UserAction attack = new UserAction("Attack") {
      @Override
      protected void onActionBegin() {
          attack();
      }
    };

    public static boolean showInventory(boolean showInv, InventoryView uiNode) {
        for (Node node : FXGL.getGameScene().getUINodes()) {

            if (node instanceof InventoryView inv) {

                if (uiNode.equals(inv)) {

                    node.setVisible(!showInv);
                    node.setManaged(!showInv);

                    return !showInv;
                }
            }
        }

        return showInv;
    }

    private void attack() {

        var entity = spawn("player-close-combat-area");

        InterpretAreaDirection interpretArea = new InterpretAreaDirection(inputStatus,
                List.of(new UpAreaDirection(getPlayer(), entity),
                        new RightAreaDirection(getPlayer(), entity),
                        new DownAreaDirection(getPlayer(), entity),
                        new LeftAreaDirection(getPlayer(), entity)));

        interpretArea.interpret();
    }
}