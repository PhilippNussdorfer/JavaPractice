package bbrz.textadventure.actions;

import bbrz.textadventure.Game;

public class BackpackAction extends AbAction {

    public BackpackAction(Game game, String ... possibleCommands) {
        super(game, "Backpack", "Shows you your items in the backpack <Command>", possibleCommands);
    }

    @Override
    public void execute(String... commandAndParams) {
        game.printBPItems();
    }

    @Override
    public String helpMessage() {
        return game.getFormatter().formatStringLength(15, getName()) + " => " + game.getFormatter().formatStringLength(100, getDescription())
                + " | Commands => " + game.getFormatter().formatStringLength(30, game.getFormatter().getPrintableCollection(getPossibleCommands()));
    }
}
