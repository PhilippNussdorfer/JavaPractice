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
    private final List<Item> equippedList = new ArrayList<>();
    @Getter
    private final int EQUIPPED_SPACE = 8;

    public boolean eqAddItems(Item item) {
        if (item.getType() != ItemType.MISC && item.getType() != ItemType.CONSUMABLE) {

            if (equippedList.isEmpty()) {
                equippedList.add(item);
            }

            else if (isNotAlreadyEquippedOrListIsFull(item)) {
                equippedList.add(item);
            }

            else {
                wrapper.outErr("The equipped item slots are already full or such an item is already equipped!");
                return false;
            }

        } else {
            wrapper.outErr("This item is not equip able: " + item.getName() + " because it is an: " + item.getType() + ".");
            return false;
        }
        return true;
    }

    private boolean isNotAlreadyEquippedOrListIsFull(Item item) {
        int artifactCount = 0;

        if (equippedList.size() == EQUIPPED_SPACE) {
            return false;
        } else {
            for (Item equippedItem : equippedList) {
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
                equippedList.remove(item);
            } else {
                game.getCurrentLocation().addItems(item);
                equippedList.remove(item);
            }
        }
    }
}
