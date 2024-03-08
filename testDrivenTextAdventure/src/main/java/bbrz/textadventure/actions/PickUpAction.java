package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import bbrz.textadventure.customException.NoFreeSpaceException;
import bbrz.textadventure.item.Item;

public class PickUpAction extends AbAction {
    public PickUpAction(Game game, String ... possibleCommands) {
        super(game, "Pick up", "To pick up an item in an location <Command> <Item name>", possibleCommands);
    }

    @Override
    public void execute(String... params) throws NoFreeSpaceException {
        if (params.length <= 1) {
            throw new IllegalArgumentException("An item name is needed to pickup an item!");
        } else {
            String itemName = params[1];

            for (Item item : this.game.getCurrentLocation().getItems()) {
                if (item.getName().equalsIgnoreCase(itemName)) {
                    this.game.getPlayer().bpAdd(item);
                    this.game.getCurrentLocation().removeItems(item);
                }
            }
        }
    }
}
