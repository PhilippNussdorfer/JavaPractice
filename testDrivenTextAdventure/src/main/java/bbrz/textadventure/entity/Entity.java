package bbrz.textadventure.entity;

import bbrz.textadventure.Game;
import bbrz.textadventure.customException.NoFreeSpaceException;
import bbrz.textadventure.item.Backpack;
import bbrz.textadventure.item.Equipped;
import bbrz.textadventure.item.Item;
import bbrz.textadventure.tools.OutputWrapper;
import lombok.Getter;

import java.util.List;

@Getter
public abstract class Entity {
    private final String name;
    private final int hp;
    private int actualHp;
    private final int armor;
    private final int dmg;
    private final OutputWrapper wrapper;
    private final AttackCalc attackCalc;
    private final Backpack bp;
    private final Equipped equipped;

    public Entity(String name, EntityStats stats) {
        this.name = name;
        this.hp = stats.getHp();
        this.actualHp = hp;
        this.armor = stats.getArmor();
        this.dmg = stats.getDmg();
        this.wrapper = stats.getWrapper();
        this.attackCalc = stats.getAttackCalc();
        this.bp = stats.getBp();
        this.equipped = stats.getEq();
    }

    public void bpAdd(Item ... items) throws NoFreeSpaceException {
        bp.bpAddItems(items);
    }

    public void bpDrop(Item ... items) {
        bp.bpRemoveItems(items);
    }

    public void addEquipment(Item item) {
        equipped.eqAddItems(item);
    }

    public void dropEquipment(Game game, Item ... items) throws NoFreeSpaceException {
        equipped.eqRemoveItems(game, items);
    }

    public int roleDmg() {
        return attackCalc.getDmgRoll(this.dmg);
    }

    public void attacked(int dmgTaken) {
        this.actualHp -= attackCalc.getsAttacked(dmgTaken, this.armor);
    }

    public List<Integer> getBoostedStats() {
        List<Item> equippedItems = equipped.getEquippedList();
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
