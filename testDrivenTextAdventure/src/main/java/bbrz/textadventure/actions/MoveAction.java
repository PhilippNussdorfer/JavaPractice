package bbrz.textadventure.actions;

import bbrz.textadventure.Game;

public class MoveAction extends AbAction {


    public MoveAction(Game game, String ... possibleCommands) {
        super(game, "Move", "To move to an other location <Command>", possibleCommands);
    }

    @Override
    public void execute(String... params) {
        game.move(params[0]);
    }

    @Override
    public String helpMessage() {
        return formatter.formatStringLength(15, getName()) + " => " + formatter.formatStringLength(100, getDescription())
                + " | Commands => " + formatter.formatStringLength(30, formatter.getPrintableCollection(getPossibleCommands()));
    }
}
