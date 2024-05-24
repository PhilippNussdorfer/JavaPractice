package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import bbrz.textadventure.item.Item;

public class DropAction extends AbAction {

    public DropAction(Game game, String ... possibleCommands) {
        super(game, "Drop", "To drop an item <Command> <Item name>", possibleCommands);
    }

    @Override
    public void execute(String... commandAndParams) {
        if (commandAndParams.length <= 1) {
            throw new IllegalArgumentException("To drop an item the item name is needed!");
        } else {
            String itemName = commandAndParams[1];

            for (Item item : this.game.getPlayer().getStats().getBp().getBackpack()) {
                if (item.getName().equalsIgnoreCase(itemName)) {
                    this.game.getCurrentLocation().addItems(item);
                    this.game.getPlayer().bpDrop(item);
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
