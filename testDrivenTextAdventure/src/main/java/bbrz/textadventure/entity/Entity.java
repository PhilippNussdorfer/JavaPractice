package bbrz.textadventure.entity;

import bbrz.textadventure.Game;
import bbrz.textadventure.OutputWrapper;
import bbrz.textadventure.customException.NoFreeSpaceException;
import bbrz.textadventure.item.Backpack;
import bbrz.textadventure.item.Equipped;
import bbrz.textadventure.item.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public abstract class Entity {
    private String name;
    private int hp;
    private int armor;
    private int dmg;
    private final OutputWrapper wrapper;
    private final AttackCalc attackCalc;
    private final Backpack bp;
    private final Equipped equipped;

    public void bpAdd(Item ... items) throws NoFreeSpaceException {
        bp.bpAddItems(items);
    }

    public void bpDrop(Item ... items) {
        bp.bpRemoveItems(items);
    }

    public void addEquipment(Item ... items) {
        equipped.eqAddItems(items);
    }

    public void dropEquipment(Game game, Item ... items) throws NoFreeSpaceException {
        equipped.eqRemoveItems(game, items);
    }

    public int roleDmg() {
        return attackCalc.getDmgRole(this.dmg);
    }

    public void attacked(int dmgTaken) {
        this.hp -= attackCalc.getsAttacked(dmgTaken, this.armor);
    }

    public List<Integer> getBoostedStats() {
        List<Item> equippedItems = equipped.getEquipped();
        int hp = this.hp;
        int armor = this.armor;
        int dmg = this.dmg;

        for (Item item : equippedItems) {
            var stats = item.getStats().getItemStats();
            hp += stats.get(0);
            armor += stats.get(1);
            dmg += stats.get(2);
        }

        return List.of(hp, armor, dmg);
    }
 }
