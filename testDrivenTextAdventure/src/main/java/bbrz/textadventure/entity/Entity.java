package bbrz.textadventure.entity;

import bbrz.textadventure.Game;
import bbrz.textadventure.customException.NoFreeSpaceException;
import bbrz.textadventure.item.Item;
import lombok.Getter;

import java.util.List;

@Getter
public abstract class Entity {
    private final String name;
    private final EntityStats stats;
    private int actualHp;

    public Entity(String name, EntityStats stats) {
        this.name = name;
        this.stats = stats;
        this.actualHp = stats.getHp();
    }

    public void bpAdd(Item ... items) throws NoFreeSpaceException {
        stats.getBp().bpAddItems(items);
    }

    public void bpDrop(Item ... items) {
        stats.getBp().bpRemoveItems(items);
    }

    public void addEquipment(Item item) {
        stats.getEq().eqAddItems(item);
    }

    public void dropEquipment(Game game, Item ... items) throws NoFreeSpaceException {
        stats.getEq().eqRemoveItems(game, items);
    }

    public int roleDmg() {
        return stats.getAttackCalc().getDmgRoll(stats.getDmg());
    }

    public void attacked(int dmgTaken) {
        this.actualHp -= stats.getAttackCalc().getsAttacked(dmgTaken, stats.getArmor());
    }

    public List<Integer> getBoostedStats() {
        List<Item> equippedItems = stats.getEq().getEquippedList();
        int hp = stats.getHp();
        int armor = stats.getArmor();
        int dmg = stats.getDmg();

        for (Item item : equippedItems) {
            var stats = item.getStats().getItemStats();
            hp += stats.get(0);
            armor += stats.get(1);
            dmg += stats.get(2);
        }

        return List.of(hp, armor, dmg);
    }
 }
