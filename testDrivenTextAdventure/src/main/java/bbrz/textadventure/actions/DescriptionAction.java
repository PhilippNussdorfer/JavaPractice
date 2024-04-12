package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import bbrz.textadventure.tools.OutputWrapper;
import bbrz.textadventure.tools.colors.TextColor;
import bbrz.textadventure.customException.CommandNotFoundException;
import bbrz.textadventure.customException.NoItemFoundException;
import bbrz.textadventure.item.Item;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class DescriptionAction extends AbAction {
    private final static String LOCATION = "location";
    private final static String LOCATION_NAME = "locationName";
    private final static String ITEM_NAME = "itemName";

    @Getter
    private final List<String> additionList = List.of(LOCATION, LOCATION_NAME, ITEM_NAME);
    OutputWrapper wrapper;
    public DescriptionAction(Game game, OutputWrapper wrapper, String ... possibleCommands) {
        super(game, "Describe", "To describe an location, item or get the location name <Command> <Addition> <Item name>", possibleCommands);
        this.wrapper = wrapper;
    }

    @Override
    public void execute(String... params) throws CommandNotFoundException, NoItemFoundException {
        if (params.length > 1) {
            if (params[1].equalsIgnoreCase(LOCATION)) {
                wrapper.outPrintlnColored(game.getCurrentLocation().getDescription() + "\n", TextColor.DARK_BROWN);
                return;
            }

            if (params[1].equalsIgnoreCase(LOCATION_NAME)) {
                wrapper.outPrintlnColored(game.getCurrentLocation().getName() + "\n", TextColor.DARK_BROWN);
                return;
            }

            if (params.length > 2 && params[1].equalsIgnoreCase(ITEM_NAME)) {
                showItemDescription(params);
                return;
            }

            throw new CommandNotFoundException("This is an command with errors: " + String.join(" ", Arrays.stream(params).toList()));
        } else {
            wrapper.outPrintlnColored(game.getCurrentLocation().getDescription() + "\n", TextColor.DARK_BROWN);
        }
    }

    private void showItemDescription(String[] params) throws NoItemFoundException {
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
        var hp = item.getStats().getItemStats().get(0);
        var armor = item.getStats().getItemStats().get(1);
        var dmg = item.getStats().getItemStats().get(2);

        wrapper.outPrintlnColored(item.getDescription() + ", it is a: " + item.getType() + ", bonus stats Hp: " + hp + ", Armor: " + armor + ", Dmg: " + dmg, TextColor.DARK_BROWN);
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

    @Override
    public String helpMessage() {
        return formatter.formatStringLength(15, getName()) + " => " + formatter.formatStringLength(100, getDescription())
                + " | Commands => " + formatter.formatStringLength(30, formatter.getPrintableCollection(getPossibleCommands()))
                + " | Additions => " + formatter.formatStringLength(30, LOCATION + ", " + LOCATION_NAME + ", " + ITEM_NAME);
    }
}
