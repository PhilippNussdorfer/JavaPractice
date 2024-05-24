package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import bbrz.textadventure.tools.colors.TextColor;
import bbrz.textadventure.tools.StringFormatter;

public class HelpAction extends AbAction {

    public HelpAction(Game game, String ... possibleCommands) {
        super(game, "Help", "Shows the help for all possible Commands <Command>", possibleCommands);
    }

    @Override
    public void execute(String... commandAndParams) {
        game.getWrapper().outPrintlnColored("=".repeat(210), TextColor.DARK_BROWN);
        for (Action action : game.getCommandInterpreter().getActionList()) {
            game.getWrapper().outPrintlnColored(action.helpMessage(), TextColor.CYAN);
        }
        game.getWrapper().outPrintlnColored("=".repeat(210) + "\n", TextColor.DARK_BROWN);
    }

    @Override
    public String helpMessage() {
        return game.getFormatter().formatStringLength(15, getName()) + " => " + game.getFormatter().formatStringLength(100, getDescription())
                + " | Commands => " + game.getFormatter().formatStringLength(30, game.getFormatter().getPrintableCollection(getPossibleCommands()));
    }
}
