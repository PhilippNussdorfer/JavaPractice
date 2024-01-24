package bbrz.adventure.game.Items;

import com.almasb.fxgl.dsl.FXGL;

import java.util.List;

public class ItemDropList {
    private static final List<Item> skeletonDropList = List.of(
            new Item(ItemDesignation.SWORD, "Old Copper Sword", "", 7, FXGL.getAssetLoader().loadImage("items/oldCopperSword.png"), new Stats(0, 2, 0, 0)),
            new Item(ItemDesignation.BOOTS, "Old Boots", "", 13, FXGL.getAssetLoader().loadImage("items/boots.png"), new Stats(5, 0, 2, 1)),
            new Item(ItemDesignation.MISC, "Sapphire", "", 35, FXGL.getAssetLoader().loadImage("items/sapphire.png"))
    );

    private static final List<Item> goblinDropList = List.of(
            new Item(ItemDesignation.MISC, "Sapphire", "", 35, FXGL.getAssetLoader().loadImage("items/sapphire.png"))
    );

    private static final List<Item> spiderDropList = List.of(
            new Item(ItemDesignation.MISC, "Sapphire", "", 35, FXGL.getAssetLoader().loadImage("items/sapphire.png"))
    );

    private static final List<Item> wolfDropList = List.of(
            new Item(ItemDesignation.MISC, "Sapphire", "", 35, FXGL.getAssetLoader().loadImage("items/sapphire.png"))
    );

    public static List<Item> getSkeletonDrops() {
        return skeletonDropList;
    }

    public static List<Item> getWolfDrops() {
        return wolfDropList;
    }

    public static List<Item> getSpiderDrops() {
        return spiderDropList;
    }

    public static List<Item> getGoblinDrops() {
        return goblinDropList;
    }
}