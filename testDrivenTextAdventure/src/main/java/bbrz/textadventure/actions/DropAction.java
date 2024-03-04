package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import bbrz.textadventure.item.Item;

public class DropAction extends AbAction {

    public DropAction(Game game, String ... possibleCommands) {
        super(game, "Drop", "To drop an item <Command> <Item name>", possibleCommands);
    }

    @Override
    public void execute(String... params) {
        if (params.length <= 1) {
            throw new IllegalArgumentException("To drop an item the item name is needed!");
        } else {
            String itemName = params[1];

            for (Item item : this.game.getPlayer().getBackpack()) {
                if (item.getName().equalsIgnoreCase(itemName)) {
                    this.game.getCurrentLocation().addItems(item);
                    this.game.getPlayer().BPRemoveItems(item);
                }
            }
        }
    }
}
