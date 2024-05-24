package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import bbrz.textadventure.customException.CommandNotFoundException;
import bbrz.textadventure.item.Item;

import java.util.Iterator;

public class EquipAction extends AbAction {
    public EquipAction(Game game, String ... possibleCommands) {
        super(game, "Equip" , "Equips an item if the slot for the item type is free <Command> <Item Name>", possibleCommands);
    }

    @Override
    public void execute(String... commandAndParams) throws CommandNotFoundException {
        if (commandAndParams.length < 2) {
            throw new CommandNotFoundException("The item name is missing!");
        }
        if (goTroughPlayerBackpackAddToPlayerEquipmentAndRemoveFromList(commandAndParams)) {
            return;
        }
        IterateOverCurrentLocationItemsAddToPlayerEquipmentAndRemoveFromList(commandAndParams);
    }

    private boolean goTroughPlayerBackpackAddToPlayerEquipmentAndRemoveFromList(String[] commandAndParams) {
        for (Item item : game.getPlayer().getStats().getBp().getBackpack()) {
            if (item.getName().equalsIgnoreCase(commandAndParams[1])) {
                var res = game.getPlayer().getStats().getEq().eqAddItems(item);
                if (res) {
                    game.getPlayer().getStats().getBp().bpRemoveItems(item);
                    return true;
                }
            }
        }
        return false;
    }

    private void IterateOverCurrentLocationItemsAddToPlayerEquipmentAndRemoveFromList(String[] params) {
        Iterator<Item> iterator = game.getCurrentLocation().getItems().iterator();

        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getName().equalsIgnoreCase(params[1])) {
                var res = game.getPlayer().getStats().getEq().eqAddItems(item);
                if (res) {
                    iterator.remove();
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
