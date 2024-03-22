package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import bbrz.textadventure.customException.CommandNotFoundException;
import bbrz.textadventure.customException.NoFreeSpaceException;
import bbrz.textadventure.item.Item;

import java.util.List;

public class SwapEquipmentAction extends AbAction {

    public SwapEquipmentAction(Game game, String ... possibleCommands) {
        super(game, "Swap Equipment", "Swaps the equipped item with an item you want to equip make sure they are from te same type\n" +
                " ".repeat(19) + "<Command> <Equipped Item Name> <Item Name of new Equipment>" + " ".repeat(41), possibleCommands);
    }

    @Override
    public void execute(String... params) throws CommandNotFoundException, NoFreeSpaceException {
        if (params.length > 2) {
            for (Item equippedItem : game.getPlayer().getEquipped().getEquippedList()) {
                if (equippedItem.getName().equalsIgnoreCase(params[1])) {

                    if (swapItems(equippedItem, params, game.getCurrentLocation().getItems())) {
                        return;
                    }

                    if (swapItems(equippedItem, params, game.getPlayer().getBp().getBackpack())) {
                        return;
                    }
                }
            }
        } else {
            throw new CommandNotFoundException("Not the correct input use help if you have problems on hao to enter this command");
        }
    }

    private boolean swapItems(Item equippedItem, String[] params, List<Item> items) throws NoFreeSpaceException {
        for (Item itemToSwapOut : items) {
            if (itemToSwapOut.getName().equalsIgnoreCase(params[2])) {
                if (equippedItem.getType() == itemToSwapOut.getType()) {
                    game.getPlayer().dropEquipment(game, equippedItem);
                    game.getPlayer().addEquipment(itemToSwapOut);
                    return true;
                } else {
                    throw new IllegalArgumentException("Please make sure that the items are from the same Type to swap them.");
                }
            }
        }
        return false;
    }
}
