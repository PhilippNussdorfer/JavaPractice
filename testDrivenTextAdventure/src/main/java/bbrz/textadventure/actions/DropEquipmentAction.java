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
    public void execute(String... params) throws NoItemFoundException, NoFreeSpaceException {
        if (params.length > 1) {
            for (Item item : game.getPlayer().getEquipped().getEquippedList()) {
                if (item.getName().equalsIgnoreCase(params[1])) {
                    game.getPlayer().dropEquipment(game, item);
                }
            }
        } else {
            throw new NoItemFoundException("Please enter an item name with the command or use help when you are not sure on how to use it.");
        }
    }

    @Override
    public String helpMessage() {
        return formatter.formatStringLength(15, getName()) + " => " + formatter.formatStringLength(100, getDescription())
                + " | Commands => " + formatter.formatStringLength(30, formatter.getPrintableCollection(getPossibleCommands()));
    }
}
