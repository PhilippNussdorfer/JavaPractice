package bbrz.adventure.game.Components;

import bbrz.adventure.game.EntityType;
import bbrz.adventure.game.Inventory.InventoryView;
import bbrz.adventure.game.Items.Item;
import bbrz.adventure.game.MamblsAdventure;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.scene.layout.BorderPane;
import lombok.Getter;
import java.util.List;

import static bbrz.adventure.game.MamblsAdventure.switchWindowOnOff;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class ItemBagComponent extends Component {

    @Getter
    private final InventoryView itemBagWindow = new InventoryView(170, 160, 12);
    private boolean isItemBagOpen = false;

    public ItemBagComponent() {
        itemBagWindow.getInvNode(
                FXGL.getAssetLoader()
                        .loadImage("gui/inventoryBodyWithoutBorder.png"),
                FXGL.getAssetLoader()
                        .loadImage("gui/inventoryHead.png"), "Item Bag");
        itemBagWindow.addTakeAllButton((BorderPane) itemBagWindow.getRoot());
    }

    @Override
    public void onUpdate(double tpf) {
        closeAndRemoveIfEmpty();
    }

    public void deleteEntityIfItemListIsEmpty() {
        if (this.itemBagWindow.isEmpty())
            entity.removeFromWorld();
    }

    private Entity getPlayer() {
        return getGameWorld().getSingleton(EntityType.PLAYER);
    }

    public void interact() {
        var i = getGameWorld().getClosestEntity(getPlayer(), MamblsAdventure.isEntityType);
        Entity itembag = null;
        if (i.isPresent()) {
            itembag = i.get();
        }
        if (itembag != null && getPlayer().isColliding(itembag)) {
            isItemBagOpen = switchWindowOnOff(isItemBagOpen, itemBagWindow.getInventoryNode());
        }
    }

    private void closeAndRemoveIfEmpty() {
        if (isItemBagOpen && itemBagWindow.isEmpty()) {
            isItemBagOpen = switchWindowOnOff(isItemBagOpen, itemBagWindow.getInventoryNode());
            deleteEntityIfItemListIsEmpty();
        }
    }

    public void checkIfItemBagIsOpen() {
        if (this.isItemBagOpen) {
            FXGL.removeUINode(this.itemBagWindow.getInventoryNode());
            this.isItemBagOpen = false;
        }
    }

    public void addDroppedItems(List<Item> droppedItems) {
        itemBagWindow.setInventory(droppedItems);
        itemBagWindow.updateInv();
    }
}
