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
}
