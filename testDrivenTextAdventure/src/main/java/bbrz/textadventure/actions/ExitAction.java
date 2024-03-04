package bbrz.textadventure.actions;

import bbrz.textadventure.Game;

public class ExitAction extends AbAction {
    public ExitAction(Game game, String ... possibleCommands) {
        super(game, "Exit", "Exits the game <Command>", possibleCommands);
    }

    @Override
    public void execute(String... params) {
        game.setLoopGame(false);
    }
}
