package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import bbrz.textadventure.OutputWrapper;
import bbrz.textadventure.colors.TextColor;
import bbrz.textadventure.customException.CommandNotFoundException;
import jdk.jshell.spi.ExecutionControl;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class DescriptionAction extends AbAction {

    private final List<String> additionList = List.of("location", "locationName", "item");
    OutputWrapper wrapper;
    public DescriptionAction(Game game, OutputWrapper wrapper, String ... possibleCommands) {
        super(possibleCommands, game);
        this.wrapper = wrapper;
    }

    @Override
    public void execute(String... params) throws ExecutionControl.NotImplementedException, CommandNotFoundException {
        if (params.length > 1) {
            if (params[1].equalsIgnoreCase(additionList.get(0))) {
                wrapper.outPrintlnColored(game.getCurrentLocation().getDescription() + "\n", TextColor.DARK_BROWN);
            }

            else if (params[1].equalsIgnoreCase(additionList.get(1))) {
                wrapper.outPrintlnColored(game.getCurrentLocation().getName() + "\n", TextColor.DARK_BROWN);
            }

            else if (params.length > 2 && params[1].equalsIgnoreCase(additionList.get(2))) {
                throw new ExecutionControl.NotImplementedException("please Implement item");
            }

            else {
                throw new CommandNotFoundException("This is an command with errors: " + String.join(" ", Arrays.stream(params).toList()));
            }
        } else {
            wrapper.outPrintlnColored(game.getCurrentLocation().getDescription() + "\n", TextColor.DARK_BROWN);
        }
    }
}
