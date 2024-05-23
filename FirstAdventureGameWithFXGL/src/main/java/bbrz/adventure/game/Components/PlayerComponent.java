package bbrz.adventure.game.Components;

import bbrz.adventure.game.Inventory.InventoryView;
import bbrz.adventure.game.Items.Stats;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import javafx.util.Duration;
import lombok.Getter;

import java.util.Random;

public class PlayerComponent extends Component {

    private final Random rnd = new Random();
    @Getter
    private int hp = 20;
    private final int maxHp = 20;
    private int increasedHp = 0;
    @Getter
    private int playerMoveSpeed = 150;
    private final int SpeedSave = 150;
    public boolean isMoving = false;
    private int increasedSpeed = 0;
    @Getter
    private int stamina = 300;
    @Getter
    private final int maxStamina = 300;
    private int increasedStamina = 0;
    @Getter
    private final int maxDmg = 6;
    @Getter
    private final int minDmg = 2;
    private int increasedDmg = 0;
    @Getter
    private final InventoryView inventoryview = new InventoryView(240, 160, 24);
    @Getter
    private final InventoryView equippedItemWindow = new InventoryView(240, 240, 8);

    public PlayerComponent() {

        this.inventoryview.getInvNode(FXGL.getAssetLoader().loadImage("gui/inventoryBodyWithoutBorder.png"),
                FXGL.getAssetLoader().loadImage("gui/inventoryHead.png"), "Inventory");;
    }

    @Override
    public void onUpdate(double tpf) {
        if (!isMoving) {
            entity.getComponent(PhysicsComponent.class).setLinearVelocity(0, 0);
        }
    }

    private int maxDmg() {
        return this.maxDmg + this.increasedDmg;
    }

    private int minDmg() {
        return this.minDmg + this.increasedDmg;
    }

    private int speed() {
        return this.playerMoveSpeed + this.increasedSpeed;
    }

    private int maxStamina() {
        return getIncMaxStamina(this.increasedStamina);
    }

    private int maxHp() {
        return getIncMaxHp(this.increasedHp);
    }

    public int getDmg() {
        int dmg = rnd.nextInt(maxDmg() - 1) + minDmg();
        return dmg * -1;
    }

    public void calculateRunning() {
        if (stamina > 0 && FXGL.geti("isRunning") == 1) {
            incStamina(-1);
            if (speed() < 350)
                incPlayerMoveSpeed(2);
        } else {
            if (speed() > 150)
                incPlayerMoveSpeed(-2);
        }
    }

    public void incPlayerMoveSpeed(int incSpeedValue) {
        if ((speed() + incSpeedValue) < 75) {
            this.playerMoveSpeed = 75;
        } else {
            if (speed() + incSpeedValue <= 600) {
                this.playerMoveSpeed += incSpeedValue;
            }
        }
    }

    private void getItemStats() {
        var itemStats = Stats.getCombinedStats(equippedItemWindow.getInventory());

        this.increasedHp = itemStats.get(0);
        this.increasedDmg = itemStats.get(1);
        this.increasedSpeed = itemStats.get(2);
        this.increasedStamina = itemStats.get(3);
    }

    public void incStamina(int incValue) {
        this.stamina = this.stamina + incValue;
    }

    public double getMaxHp() {
        return this.maxHp + increasedHp;
    }

    private void incHp(int incValue) {
        if (this.hp + incValue < maxHp()) {
            this.hp += incValue;
        } else {
            this.hp = maxHp();
        }
    }

    private int getIncMaxHp(int incValue) {
        if ((this.maxHp + incValue) < 1) {
            return  1;
        } else {
            if (this.maxHp + incValue < 1500) {
                return this.maxHp + incValue;
            }
            return  1500;
        }
    }

    private int getIncMaxStamina(int incValue) {
        if ((this.maxStamina + incValue) < 150) {
            return 150;
        } else {
            if (this.maxStamina + incValue < 750) {
                return this.maxStamina + incValue;
            }
            return 750;
        }
    }

    public void spanAndFireBow() {
        AnimationChannel SpanBow = new AnimationChannel(FXGL.image("bow/SpanBow.png"), Duration.seconds(0.5), 5);
        Texture bowIdle = new Texture(FXGL.getAssetLoader().loadImage("bow/BowIdle.png"));
        AnimatedTexture texture = new AnimatedTexture(SpanBow);
    }
}
