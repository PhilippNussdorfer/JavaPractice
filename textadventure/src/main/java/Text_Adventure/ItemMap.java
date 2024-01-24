package Text_Adventure;

import Text_Adventure.Printer.ANSIPrinter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ItemMap implements Serializable {

    private Map<String, Item> allItems;


    public ItemMap() {
        this.allItems = new HashMap<>();
        init();
    }

    private void init() {

        Item holeWithDirtyWater = new Item("Hole-with-dirty-Water", "", false);
        Item litOilLamp = new Item("Lit-Oil-Lamp", "It is a Lit Oil Lamp, it gives out some warmth and light");
        Item cleanedOilLamp = new Item("Clean-Oil-Lamp", "Is a clean Oil Lamp.");
        Item bucketWithRope = new Item("Bucket-with-a-rope", "It has a rope on it's handle, could be used to draw water out of a well.");
        Item wetSponge = new Item("Wet-Sponge", "Its wet, it can be used to clean objects.");
        Item bucketWithWaterAndRope = new Item("Water-Bucket", "is a bucket full of water, a Sponge would be nice.");
        Item dirtySponge = new Item("Dirty-Sponge", "Eww the Sponge is dirty, clean it to use it again.");
        Item bucketWithDirtyWater = new Item("Dirty-Water-bucket", "Now the water in the bucket is Dirty, dump it somewhere to use the bucket again.");

        addItem("dWaterB", bucketWithDirtyWater);
        addItem("litL", litOilLamp);
        addItem("dWaterH", holeWithDirtyWater);
        addItem("cleanL", cleanedOilLamp);
        addItem("ropeB", bucketWithRope);
        addItem("wetSpo", wetSponge);
        addItem("waterB", bucketWithWaterAndRope);
        addItem("dirtySpo", dirtySponge);

        Item axe = new Item("Old-axe", "It is an old rusty axe, it seems to be used a lot.",
                new ANSIPrinter("axe" ,"0006024200000000000000000000000000000000000000000094000000000000000000080008000000080008000" +
                        "80000000B000000000008000800000000000B000800080008000800000000000B000800080008000800080008000B000800080008000800080008000B"));
        Item dirtyOilLamp = new Item("Dirty-Oil-Lamp", "It is dusty but it has oil in it seems to be a good Light source when lighted.");
        Item matches = new Item("Old-matches", "The matches seems to be a bit to damp to use, they could be dried up by a warm place.");
        Item matches2 = new Item("Old-matches", "The matches seems to be a bit to damp to use, they could be dried up by a warm place.");
        Item firePlace = new Item("Fire-Place", "It is a warm place.", false);
        Item bucket = new Item("Old-water-bucket", "It is a bucket to fetch water.");
        Item rope = new Item("Rope", "Is a old rope, but it seems to be sturdy enough to be used.");
        Item well = new Item("Well", "Is a Old Well with a lot of water in it.", false);
        Item sponge = new Item("Sponge", "Its a sponge to clean objects.");
        Item holeInTheGround = new Item("Hole", "Its a hole in the ground.", false);

        addItem("axe", axe);
        addItem("dirtyL", dirtyOilLamp);
        addItem("mat1", matches);
        addItem("mat2", matches2);
        addItem("fireP", firePlace);
        addItem("bucket", bucket);
        addItem("rope", rope);
        addItem("well", well);
        addItem("spo", sponge);
        addItem("holeG", holeInTheGround);
    }

    public void addItem(String key, Item item) {
        this.allItems.put(key, item);
    }

    public Item getItem(String key) {
        return this.allItems.get(key);
    }
}
