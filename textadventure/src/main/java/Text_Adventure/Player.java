package Text_Adventure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {

    private String name;
    private List<Item> scrip;

    public Player(String name) {

        this.name = name;
        scrip = new ArrayList<>();
    }

    public String getName() {return name;}
    public List<Item> getScrip() {return scrip;}

    public void addItemToScrip(Item item) {
        if (scrip.size() < 7) {
            scrip.add(item);
        } else {
            System.out.println("your scrip is full you can't pick up more items unless you drop one fore it.");
        }
    }

    public Item getItemWithStrInv(String name) {

        for (Item item : scrip) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public void dropItem(Item item) {
        if (scrip.isEmpty()) {
            System.out.println("Your scrip is empty.");
        } else if (scrip.contains(item)){
            scrip.remove(item);
        } else {
            System.out.println("The item you want to drop is not in the scrip");
        }
    }

    public void showItemsInScrip() {

        int counter = 0;
        for (Item item : scrip) {
            System.out.print("[ " + item.getName() + " ]   ");
            counter++;
        }
        for (int i = counter; i < 6; i++) {
            System.out.print("[  ]   ");
        }
        System.out.println("\n");
    }

    public void setName(String name) {
        this.name = name;
    }
}
