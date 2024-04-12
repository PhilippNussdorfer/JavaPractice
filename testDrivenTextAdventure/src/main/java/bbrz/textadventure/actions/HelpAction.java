package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import bbrz.textadventure.tools.colors.TextColor;
import bbrz.textadventure.tools.StringFormatting;

public class HelpAction extends AbAction {

    private final StringFormatting formatter = new StringFormatting();

    public HelpAction(Game game, String ... possibleCommands) {
        super(game, "Help", "Shows the help for all possible Commands <Command>", possibleCommands);
    }

    @Override
    public void execute(String... params) {
        game.getWrapper().outPrintlnColored("=".repeat(210), TextColor.DARK_BROWN);
        for (Action action : game.getInterpreter().getActionList()) {
            game.getWrapper().outPrintlnColored(action.helpMessage(), TextColor.CYAN);
        }
        game.getWrapper().outPrintlnColored("=".repeat(210) + "\n", TextColor.DARK_BROWN);
    }

    @Override
    public String helpMessage() {
        return formatter.formatStringLength(15, getName()) + " => " + formatter.formatStringLength(100, getDescription())
                + " | Commands => " + formatter.formatStringLength(30, formatter.getPrintableCollection(getPossibleCommands()));
    }
}
