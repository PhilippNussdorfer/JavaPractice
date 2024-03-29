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
    public void execute(String... params) throws CommandNotFoundException {
        if (params.length < 2) {
            throw new CommandNotFoundException("The item name is missing!");
        }
        if (goTroughPlayerBackpackAddToPlayerEquipmentAndRemoveFromList(params)) {
            return;
        }
        IterateOverCurrentLocationItemsAddToPlayerEquipmentAndRemoveFromList(params);
    }

    private boolean goTroughPlayerBackpackAddToPlayerEquipmentAndRemoveFromList(String[] params) {
        for (Item item : game.getPlayer().getBp().getBackpack()) {
            if (item.getName().equalsIgnoreCase(params[1])) {
                var res = game.getPlayer().getEquipped().eqAddItems(item);
                if (res) {
                    game.getPlayer().getBp().bpRemoveItems(item);
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
                var res = game.getPlayer().getEquipped().eqAddItems(item);
                if (res) {
                    iterator.remove();
                }
            }
        }
    }

    @Override
    public String helpMessage() {
        return formatter.formatStringLength(15, getName()) + " => " + formatter.formatStringLength(100, getDescription())
                + " | Commands => " + formatter.formatStringLength(30, formatter.getPrintableCollection(getPossibleCommands()));
    }
}
