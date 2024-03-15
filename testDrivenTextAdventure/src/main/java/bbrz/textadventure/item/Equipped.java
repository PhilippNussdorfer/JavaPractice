package bbrz.textadventure.item;

import bbrz.textadventure.Game;
import bbrz.textadventure.tools.OutputWrapper;
import bbrz.textadventure.customException.NoFreeSpaceException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class Equipped {

    private final OutputWrapper wrapper;
    @Getter
    private final List<Item> equipped = new ArrayList<>();
    @Getter
    private final int EQUIPPED_SPACE = 8;

    public void eqAddItems(Item ... items) {
        for (Item item : items) {
            if (item.getType() != ItemType.MISC && item.getType() != ItemType.CONSUMABLE) {

                if (equipped.isEmpty()) {
                    equipped.add(item);
                }

                else if (isNotAlreadyEquippedOrListIsFull(item)) {
                    equipped.add(item);
                }

                else {
                    wrapper.outErr("The equipped item slots are already full or such an item is already equipped!");
                }

            } else {
                wrapper.outErr("This item is not equip able: " + item.getName() + " because it is an: " + item.getType() + ".");
            }
        }
    }

    private boolean isNotAlreadyEquippedOrListIsFull(Item item) {
        int artifactCount = 0;

        if (equipped.size() == EQUIPPED_SPACE) {
            return false;
        } else {
            for (Item equippedItem : equipped) {
                if (item.getType() == ItemType.ARTIFACT) {

                    if (equippedItem.getType() == item.getType()) {
                        artifactCount++;
                    }

                    if (artifactCount == 2) {
                        return false;
                    }

                } else {

                    if (item.getType() == equippedItem.getType()) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    public void eqRemoveItems(Game game, Item ... items) throws NoFreeSpaceException {
        for (Item item : items) {
            if (game.getPlayer().getBp().getBackpack().size() < game.getPlayer().getBp().getBACKPACK_SPACE()) {
                game.getPlayer().getBp().bpAddItems(item);
                equipped.remove(item);
            } else {
                game.getCurrentLocation().addItems(item);
                equipped.remove(item);
            }
        }
    }
}
