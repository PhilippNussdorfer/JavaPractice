package bbrz.textadventure.actions;

import bbrz.textadventure.Game;

public class BackpackAction extends AbAction {

    public BackpackAction(Game game, String name, String description, String ... possibleCommands) {
        super(game, name, description, possibleCommands);
    }

    @Override
    public void execute(String... params) {
        game.printBPItems();
    }
}
