package bbrz.textadventure.item;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ItemStats {
    private int hp;
    private int armor;
    private int dmg;

    /**
     * @return an List of integer in this order (hp, armor, dmg)
     */
    public List<Integer> getStats() {
        return List.of(hp, armor, dmg);
    }
}
