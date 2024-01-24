package Text_Adventure;

import Text_Adventure.Printer.ANSIPrinter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Item implements Serializable {

    private String name;
    private String description;
    private boolean pickAble;
    private boolean active;
    private ANSIPrinter itemIMG;
    private Map<Room, List<Item>> items;

    public Item(String name, String description, ANSIPrinter itemHexIMG) {

        this.itemIMG = itemHexIMG;
        this.name = name;
        this.description = description;
        this.pickAble = true;
        this.active = true;
        items = new HashMap<>();
    }

    public String getName() {return name;}
    public String getDescription() {return description;}
    public Map<Room, List<Item>> getItems() {return items;}
    public boolean isPickAble() {return pickAble;}

    public Item(String name, String description) {

        this.itemIMG = null;
        this.name = name;
        this.description = description;
        this.pickAble = true;
        this.active = true;
        items = new HashMap<>();
    }

    public Item(String name, String description, boolean pickAble) {

        this(name, description);
        this.itemIMG = null;
        this.pickAble = pickAble;
        this.active = true;
    }

    public Item(String name, String description, boolean pickAble, ANSIPrinter itemHexIMG) {

        this(name, description, itemHexIMG);
        this.itemIMG = null;
        this.pickAble = pickAble;
        this.active = true;
    }

    public void printItemIMG() {
        if (itemIMG != null) {
            itemIMG.print();
        }
    }
}
