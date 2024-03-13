package bbrz.textadventure.item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemStatsTest {

    ItemStats stats = new ItemStats(12, 3, 5);
    ItemStats secStats = new ItemStats();

    @Test
    void getStats() {
        var stats = this.stats.getItemStats();
        assertEquals(12, stats.get(0));
        assertEquals(3, stats.get(1));
        assertEquals(5, stats.get(2));

        stats = this.secStats.getItemStats();
        for (int stat : stats) {
            assertEquals(0, stat);
        }
    }
}