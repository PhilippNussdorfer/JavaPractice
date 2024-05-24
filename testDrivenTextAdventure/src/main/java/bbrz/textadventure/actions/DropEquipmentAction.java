package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import bbrz.textadventure.customException.NoFreeSpaceException;
import bbrz.textadventure.customException.NoItemFoundException;
import bbrz.textadventure.item.Item;

public class DropEquipmentAction extends AbAction {

    public DropEquipmentAction(Game game, String ... possibleCommands) {
        super(game, "Drop Equipment", "drops the item in the backpack or to the floor when the backpack is full <Command> <Item Name>", possibleCommands);
    }

    @Override
    public void execute(String... commandAndParams) throws NoItemFoundException, NoFreeSpaceException {
        if (commandAndParams.length > 1) {
            for (Item item : game.getPlayer().getStats().getEq().getEquippedList()) {
                if (item.getName().equalsIgnoreCase(commandAndParams[1])) {
                    game.getPlayer().dropEquipment(game, item);
                    return;
                }
            }
        } else {
            throw new NoItemFoundException("Please enter an item name with the command or use help when you are not sure on how to use it.");
        }
    }

    @Override
    public String helpMessage() {
        return game.getFormatter().formatStringLength(15, getName()) + " => " + game.getFormatter().formatStringLength(100, getDescription())
                + " | Commands => " + game.getFormatter().formatStringLength(30, game.getFormatter().getPrintableCollection(getPossibleCommands()));
    }
}
