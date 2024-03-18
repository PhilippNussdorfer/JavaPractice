package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import bbrz.textadventure.customException.CommandNotFoundException;
import bbrz.textadventure.item.Item;

public class EquipAction extends AbAction {
    public EquipAction(Game game, String ... possibleCommands) {
        super(game, "Equip" , "Equips an item if the slot for the item type is free <Command> <Item Name>", possibleCommands);
    }

    @Override
    public void execute(String... params) throws CommandNotFoundException {
        if (params.length < 2) {
            throw new CommandNotFoundException("The item name is missing!");
        }

        for (Item item : game.getPlayer().getBp().getBackpack()) {
            if (item.getName().equalsIgnoreCase(params[1])) {
                var res = game.getPlayer().getEquipped().eqAddItems(item);
                if (res) {
                    game.getPlayer().getBp().bpRemoveItems(item);
                }
                return;
            }
        }

        for (Item item : game.getCurrentLocation().getItems()) {
            if (item.getName().equalsIgnoreCase(params[1])) {
                var res = game.getPlayer().getEquipped().eqAddItems(item);
                if (res) {
                    game.getCurrentLocation().removeItems(item);
                }
            }
        }
    }
}
