package bbrz.adventure.game.EnemyDrops;

import bbrz.adventure.game.Components.EnemyIndicator;
import bbrz.adventure.game.Items.Item;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemyDrops {
    @Getter
    private final EnemyIndicator indicator;
    private final List<Item> DropAbleItems;
    private final Random rnd = new Random();

    public EnemyDrops(EnemyIndicator indicator, List<Item> DropAbleItems) {
        this.indicator = indicator;
        this.DropAbleItems = DropAbleItems;
    }

    public List<Item> getDroppedItems() {
        List<Item> itemDrops = new ArrayList<>();
        int getRndDrop = rnd.nextInt(this.DropAbleItems.size());
        itemDrops.add(this.DropAbleItems.get(getRndDrop));

        for (Item item : this.DropAbleItems) {
            getRndDrop = rnd.nextInt(this.DropAbleItems.size());
            if (rnd.nextInt(this.DropAbleItems.size()) == getRndDrop) {
                itemDrops.add(this.DropAbleItems.get(getRndDrop));
            }
        }

        return itemDrops;
    }
}
