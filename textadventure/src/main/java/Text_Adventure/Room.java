package Text_Adventure;

import Text_Adventure.Printer.ANSIPrinter;

import java.io.Serializable;
import java.util.*;

public class Room implements Serializable {

    private String name;
    private String description;
    private ANSIPrinter roomIMG;
    private Map <String, Room> exits;
    private List<Item> itemsInTheRoom;

    public Room(String name, String description, ANSIPrinter roomHexStrIMG) {

        this.name = name;
        this.description = description;
        this.roomIMG = roomHexStrIMG;

        this.exits = new HashMap<>();
        this.itemsInTheRoom = new ArrayList<>();
    }

    public String getName() {return name;}
    public String getDescription() {return description;}
    public Map<String, Room> getExits() {return exits;}
    public List<Item> getItemsInTheRoom() {return itemsInTheRoom;}

    public Room(String name, String description) {

        this.name = name;
        this.description = description;
        this.roomIMG = null;

        this.exits = new HashMap<>();
        this.itemsInTheRoom = new ArrayList<>();
    }

    public void addItem(Item ... items) {

        this.itemsInTheRoom.addAll(Arrays.asList(items));
    }

    public void printRoomIMG() {
        if (this.roomIMG != null) {
            this.roomIMG.print();
        }
    }

    public void removeItem(Item item) {
        this.itemsInTheRoom.remove(item);
    }

    public Item getItemWithStrRoom(String name) {

        for (Item item : this.itemsInTheRoom) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public void addRoom(String direction, Room room) {

        this.exits.put(direction, room);
    }

    public Room findExit(String direction) {

        return this.exits.get(direction.toUpperCase());
    }

    public String getDirection() {

        List<String> keys = new ArrayList<>(exits.keySet());
        StringBuilder finishedStr = new StringBuilder();

        for (String key : keys) {
            finishedStr.append("Those are the directions you can go: ").append(key).append(" -> ").append(this.exits.get(key).getName()).append("\n");
        }

        return finishedStr.toString();
    }
}
