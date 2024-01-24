package bbrz.adventure.game.Inventory;

import bbrz.adventure.game.Items.Item;
import javafx.scene.Node;
import javafx.scene.layout.Background;

import java.util.ArrayList;
import java.util.List;

public class InventoryUniversal {
    private final int width;
    private final int height;
    private final Background background;
    private final int capacity;
    // for Stacks will be implemented when an idea comes up how to do it.
    // private List<StackItems> itemList;
    private final List<Item> itemList;
    private final Node itemWindows;

    InventoryUniversal(int width, int height, Background background, int capacity, Node itemWindows) {
        this.itemList = new ArrayList<>();

        this.width = width;
        this.height = height;
        this.background = background;
        this.capacity = capacity;
        this.itemWindows = itemWindows;
    }

    public boolean addItemToInventory(Item item) {
        if (this.itemList.size() <= capacity) {
            this.itemList.add(item);
            return true;
        }
        return false;
    }

    public List<Item> getItemList() {
        return this.itemList;
    }

    public void openInventory() {

    }

    public void closeInventory() {

    }
}
