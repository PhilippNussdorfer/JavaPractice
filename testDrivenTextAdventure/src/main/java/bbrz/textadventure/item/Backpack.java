package bbrz.textadventure.item;

import bbrz.textadventure.tools.OutputWrapper;
import bbrz.textadventure.customException.NoFreeSpaceException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class Backpack {

    OutputWrapper wrapper;
    @Getter
    private final List<Item> backpack = new ArrayList<>();
    @Getter
    private final int BACKPACK_SPACE = 12;

    public void bpAddItems(Item ... items) throws NoFreeSpaceException {
        for (Item item : items) {
            if (this.backpack.size() + 1 <= BACKPACK_SPACE) {
                backpack.add(item);
            } else {
                throw new NoFreeSpaceException("Your backpack is full you cant carry more items with you!");
            }
        }
    }

    public void bpRemoveItems(Item ... items) {
        for (Item item : items) {
            if (this.backpack.contains(item)) {
                this.backpack.remove(item);
            } else {
                wrapper.outErr("Could not find the item: " + item.getName());
            }
        }
    }
}
