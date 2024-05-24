package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import bbrz.textadventure.customException.NoFreeSpaceException;
import bbrz.textadventure.item.Item;

public class PickUpAction extends AbAction {
    public PickUpAction(Game game, String ... possibleCommands) {
        super(game, "Pick up", "To pick up an item in an location <Command> <Item name>", possibleCommands);
    }

    @Override
    public void execute(String... commandAndParams) throws NoFreeSpaceException {
        if (commandAndParams.length <= 1) {
            throw new IllegalArgumentException("An item name is needed to pickup an item!");
        } else {
            String itemName = commandAndParams[1];

            for (Item item : this.game.getCurrentLocation().getItems()) {
                if (item.getName().equalsIgnoreCase(itemName)) {
                    this.game.getPlayer().bpAdd(item);
                    this.game.getCurrentLocation().removeItems(item);
                    return;
                }
            }
        }
    }

    @Override
    public String helpMessage() {
        return game.getFormatter().formatStringLength(15, getName()) + " => " + game.getFormatter().formatStringLength(100, getDescription())
                + " | Commands => " + game.getFormatter().formatStringLength(30, game.getFormatter().getPrintableCollection(getPossibleCommands()));
    }
}
