package bbrz.adventure.game.Items;

import javafx.scene.image.Image;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Item{
    private final static AtomicInteger count = new AtomicInteger(0);
    private String name;
    private int value;
    private Image img;
    private String description;
    private boolean isStackable;
    private int maxStackSize;
    private Stats stats;
    private int ID;
    private ItemDesignation designation;

    public Item(ItemDesignation designation, String name, String description, int value, Image img) {
        this.maxStackSize = 0;
        this.isStackable = false;
        this.stats = new Stats(0, 0, 0, 0);
        this.ID = count.incrementAndGet();

        this.designation = designation;
        this.img = img;
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public Item(ItemDesignation designation, String name, String description, int value, int maxStackSize, Image img) {
        this.isStackable = true;
        this.stats = new Stats(0, 0, 0, 0);
        this.ID = count.incrementAndGet();

        this.designation = designation;
        this.name = name;
        this.description = description;
        this.value = value;
        this.maxStackSize = maxStackSize;
        this.img = img;
    }

    public Item(ItemDesignation designation, String name, String description, int value, Image img, Stats stats) {
        this.maxStackSize = 0;
        this.isStackable = false;
        this.ID = count.incrementAndGet();

        this.designation = designation;
        this.stats = stats;
        this.img = img;
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public Item(ItemDesignation designation, String name, String description, int value, int maxStackSize, Image img, Stats stats) {
        this.isStackable = true;
        this.ID = count.incrementAndGet();

        this.designation = designation;
        this.stats = stats;
        this.name = name;
        this.description = description;
        this.value = value;
        this.maxStackSize = maxStackSize;
        this.img = img;
    }

    public Item(ItemDesignation designation, String name, String description, int value, int maxStackSize, boolean isStackable, Image img, Stats stats) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.img = img;
        this.stats = stats;
        this.isStackable = isStackable;
        this.designation = designation;

        if (this.isStackable) {
            this.maxStackSize = maxStackSize;
        } else {
            this.maxStackSize = 0;
        }

        this.ID = count.incrementAndGet();
    }

    private Item() {}

    public static Item createNullItem() {
        Item item = new Item();
        item.name = "";
        item.value = 0;
        item.img = null;
        item.description = "";
        item.isStackable = false;
        item.maxStackSize = 0;
        item.stats = null;
        item.ID = 0;
        item.designation = ItemDesignation.NULL;
        return item;
    }
}
