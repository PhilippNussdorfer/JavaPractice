package bbrz.textadventure.locations;

import bbrz.textadventure.customException.RoomNotFoundException;
import bbrz.textadventure.rules.MapRuleMark;
import bbrz.textadventure.item.Item;
import bbrz.textadventure.tools.StringFormatter;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Location {

    @Getter
    private final String name;
    @Getter
    private final String description;
    @Getter
    private final MapRuleMark mark;
    private final List<LocationPointer> pointers = new ArrayList<>();
    @Getter
    private final List<Item> items = new ArrayList<>();
    @Getter
    private Position pos = null;
    private final StringFormatter formatter = new StringFormatter();

    public Location(String name, String description, MapRuleMark mark) {
        this.name = name;
        this.description = description;
        this.mark = mark;
    }

    public void setPosition(int x, int y) {
        pos = new Position(x, y);
    }

    public void setPosition(Position pos) {
        this.pos = pos;
    }

    public Location getLocation(String direction) throws RoomNotFoundException {
        for (LocationPointer pointer : pointers) {
            if (pointer.isDirection(direction)) {
                return pointer.getTarget();
            }
        }
        throw new RoomNotFoundException("There is no room in this direction.");
    }

    public void addPointers(LocationPointer ... pointers) {
        this.pointers.addAll(Arrays.asList(pointers));
    }

    public void addItems(Item ... items) {
        this.items.addAll(Arrays.asList(items));
    }

    public void removeItems(Item ... items) {
        for (Item item : items) {
            this.items.remove(item);
        }
    }

    public List<String> getPointerDirections() {
        List<String> result = new ArrayList<>();

        for (LocationPointer pointer : pointers) {
            result.add(formatter.getPrintableCollection(pointer.getDirection().getValues()) + " => " + pointer.getTarget().getName());
        }

        return result;
    }

    public Location cloneLocation() {
        return new Location(this.name, this.description, this.mark);
    }
}