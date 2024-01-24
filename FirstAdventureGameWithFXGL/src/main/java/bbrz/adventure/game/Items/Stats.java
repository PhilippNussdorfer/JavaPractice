package bbrz.adventure.game.Items;

import java.util.ArrayList;
import java.util.List;

public class Stats {
    private static final List<Integer> statList = new ArrayList<>();
    private final int maxInOrDegrease = 3;

    public Stats(int hp, int dmg, int speed, int stamina) {
        statList.add(hp);
        statList.add(dmg);
        statList.add(speed);
        statList.add(stamina);
    }

    public List<Integer> getList() {
        return statList;
    }

    public void incStatList(int hp, int dmg, int speed, int stamina) {
        if (hp > this.maxInOrDegrease || dmg > this.maxInOrDegrease || speed > this.maxInOrDegrease || stamina > this.maxInOrDegrease) {
            throw new IllegalArgumentException("Pleas keep the increase by maximal " + maxInOrDegrease);
        }

        statList.set(0, statList.get(0) + hp);
        statList.set(1, statList.get(1) + dmg);
        statList.set(2, statList.get(2) + speed);
        statList.set(3, statList.get(3) + stamina);
    }

    public static List<Integer> getCombinedStats(List<Item> items) {
        List<Integer> combinedStats = new ArrayList<>(new Stats(0, 0, 0, 0).getList());

        for (Item item : items) {
            for (int i = 0; i < combinedStats.size(); i++) {
                combinedStats.set(i, combinedStats.get(i) + item.getStats().getList().get(i));
            }
        }
        return combinedStats;
    }
}
