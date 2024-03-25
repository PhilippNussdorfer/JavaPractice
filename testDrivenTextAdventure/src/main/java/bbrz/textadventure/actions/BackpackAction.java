package bbrz.textadventure.actions;

import bbrz.textadventure.Game;

public class BackpackAction extends AbAction {

    public BackpackAction(Game game, String ... possibleCommands) {
        super(game, "Backpack", "Shows you your items in the backpack <Command>", possibleCommands);
    }

    @Override
    public void execute(String... params) {
        game.printBPItems();
    }

    @Override
    public String helpMessage() {
        return formatter.formatStringLength(15, getName()) + " => " + formatter.formatStringLength(100, getDescription())
                + " | Commands => " + formatter.formatStringLength(30, formatter.getPrintableCollection(getPossibleCommands()));
    }
}
