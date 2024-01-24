package bbrz.adventure.game.EnemyDrops;

import bbrz.adventure.game.Components.EnemyComponent;
import bbrz.adventure.game.Items.Item;

import java.util.List;

public class InterpretDrops {

    private final List<EnemyDrops> enemyDrops;
    private final EnemyComponent component;

    public InterpretDrops(List<EnemyDrops> enemyDrops, EnemyComponent component) {
        this.enemyDrops = enemyDrops;
        this.component = component;
    }

    public List<Item> interpret() {
        for (EnemyDrops drops : this.enemyDrops) {
            if (drops.getIndicator() == component.getIndicator()) {
                return drops.getDroppedItems();
            }
        }
        return null;
    }
}
