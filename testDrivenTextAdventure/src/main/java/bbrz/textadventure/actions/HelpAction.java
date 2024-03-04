package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import bbrz.textadventure.colors.TextColor;

import java.util.List;

public class HelpAction extends AbAction {

    public HelpAction(Game game, String ... possibleCommands) {
        super(game, "Help", "Shows the help for all possible Commands <Command>", possibleCommands);
    }

    @Override
    public void execute(String... params) {
        var actions = game.getInterpreter().getActionList();

        for (Action action : actions) {
            if (action instanceof DescriptionAction descAction) {
                game.getWrapper().outPrintlnColored(action.getName() + " => " + action.getDescription()
                        + "Commands: " + getPrintableCollection(action.getPossibleCommands()) + " Additions: " + getPrintableCollection(descAction.getAdditionList()), TextColor.CYAN);
            } else {
                game.getWrapper().outPrintlnColored(action.getName() + " => " + action.getDescription()
                        + " Commands: " + getPrintableCollection(action.getPossibleCommands()), TextColor.CYAN);
            }
        }
    }

    private String getPrintableCollection(String[] strArr) {
        return String.join(", ", strArr);
    }

    private String getPrintableCollection(List<String> strArr) {
        return String.join(", ", strArr);
    }
}
