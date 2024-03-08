package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import bbrz.textadventure.OutputWrapper;
import bbrz.textadventure.colors.TextColor;
import bbrz.textadventure.customException.CommandNotFoundException;
import bbrz.textadventure.customException.NoItemFoundException;
import bbrz.textadventure.item.Item;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class DescriptionAction extends AbAction {

    @Getter
    private final List<String> additionList = List.of("location", "locationName", "item");
    OutputWrapper wrapper;
    public DescriptionAction(Game game, OutputWrapper wrapper, String ... possibleCommands) {
        super(game, "Describe", "To describe an location, item or get the location name <Command> <Addition> <Item name>", possibleCommands);
        this.wrapper = wrapper;
    }

    @Override
    public void execute(String... params) throws CommandNotFoundException, NoItemFoundException {
        if (params.length > 1) {
            if (params[1].equalsIgnoreCase(additionList.get(0))) {
                wrapper.outPrintlnColored(game.getCurrentLocation().getDescription() + "\n", TextColor.DARK_BROWN);
            }

            else if (params[1].equalsIgnoreCase(additionList.get(1))) {
                wrapper.outPrintlnColored(game.getCurrentLocation().getName() + "\n", TextColor.DARK_BROWN);
            }

            else if (params.length > 2 && params[1].equalsIgnoreCase(additionList.get(2))) {
                var locationItems = game.getCurrentLocation().getItems();
                var playerBp = game.getPlayer().getBp().getBackpack();
                var itemName = params[2];

                var item = getItemFromString(locationItems, itemName);
                if (item == null) {
                    item = getItemFromString(playerBp, itemName);
                    if (item == null) {
                        throw new NoItemFoundException("Could not find the item with the name: " + itemName + "!");
                    }
                }
                wrapper.outPrintlnColored(item.getDescription(), TextColor.DARK_BROWN);
            }

            else {
                throw new CommandNotFoundException("This is an command with errors: " + String.join(" ", Arrays.stream(params).toList()));
            }
        } else {
            wrapper.outPrintlnColored(game.getCurrentLocation().getDescription() + "\n", TextColor.DARK_BROWN);
        }
    }

    private Item getItemFromString(List<Item> items, String name) {
        Item result = null;
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                result = item;
            }
        }
        return result;
    }
}
