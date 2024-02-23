package bbrz.textadventure.actions;

import bbrz.textadventure.Game;

public class ExitAction extends AbAction {
    public ExitAction(Game game, String ... possibleCommands) {
        super(possibleCommands, game);
    }

    @Override
    public void execute(String... params) {
        game.setLoopGame(false);
    }
}
