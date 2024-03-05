package bbrz.textadventure.entity;

import bbrz.textadventure.Game;
import bbrz.textadventure.OutputWrapper;
import bbrz.textadventure.customException.NoFreeSpaceException;
import bbrz.textadventure.item.Item;
import bbrz.textadventure.item.ItemType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@AllArgsConstructor
public abstract class Entity {
    private String name;
    private int hp;
    private int armor;
    private int dmg;
    private final OutputWrapper wrapper;

    private final int BACKPACK_SPACE = 12;
    private final int EQUIPPED_SPACE = 8;
    private final List<Item> backpack = new ArrayList<>();
    private final List<Item> equipped = new ArrayList<>();

    public void BPAddItems(Item ... items) throws NoFreeSpaceException {
        for (Item item : items) {
            if (this.backpack.size() + 1 <= BACKPACK_SPACE) {
                backpack.add(item);
            } else {
                throw new NoFreeSpaceException("Your backpack is full you cant carry more items with you!");
            }
        }
    }

    public void BPRemoveItems(Item ... items) {
        for (Item item : items) {
            if (this.backpack.contains(item)) {
                this.backpack.remove(item);
            } else {
                wrapper.outErr("Could not find the item: " + item.getName());
            }
        }
    }

    public void EQAddItems(Item ... items) {
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
                wrapper.outErr("This item is not equip able: " + item.getName());
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

    public void EQRemoveItems(Game game, Item ... items) {
        for (Item item : items) {
            if (backpack.size() != BACKPACK_SPACE) {
                backpack.add(item);
                equipped.remove(item);
            } else {
                game.getCurrentLocation().addItems(item);
                equipped.remove(item);
            }
        }
    }

    public void getsAttacked(int dmg) {
        double damageReduction = (double) (this.armor) / 100;
        int actualDmg = (int) (dmg - dmg * damageReduction);

        if (actualDmg == 0) {
            actualDmg = 1;
        }

        this.hp -= actualDmg;
    }

    public int getDmgRole() {
        int minDmg = (int) ((double) (this.getDmg()) * 0.80);

        return new Random().nextInt(minDmg - 1, this.getDmg() + 1);
    }
}
