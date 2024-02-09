package bbrz.textadventure.actions;

import bbrz.textadventure.Game;

public class MoveAction extends AbAction {


    public MoveAction(Game game, String ... possibleCommands) {
        super(possibleCommands, game);
    }

    @Override
    public void execute(String... params) {
        game.move(params[0]);
    }
}
