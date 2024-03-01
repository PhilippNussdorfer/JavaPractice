package bbrz.textadventure.entity;

import bbrz.textadventure.OutputWrapper;
import bbrz.textadventure.customException.NoFreeSpaceException;
import bbrz.textadventure.item.Item;
import jdk.jshell.spi.ExecutionControl;
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

    public void EQAddItems(Item ... items) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Pleas implement this to use!");
    }

    public void EQRemoveItems(Item ... items) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Pleas implement this to use!");
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
