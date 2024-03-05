package bbrz.textadventure.item;

import java.util.List;

public class ItemStats {
    private final int hp;
    private final int armor;
    private final int dmg;

    public ItemStats(int hp, int armor, int dmg) {
        this.hp = hp;
        this.armor = armor;
        this.dmg = dmg;
    }

    public ItemStats() {
        this.hp = 0;
        this.armor = 0;
        this.dmg = 0;
    }

    /**
     * @return a List of integer in this order (hp, armor, dmg)
     */
    public List<Integer> getStats() {
        return List.of(hp, armor, dmg);
    }
}
