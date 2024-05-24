package bbrz.textadventure;

import bbrz.textadventure.entity.Player;
import bbrz.textadventure.tools.colors.TextColor;
import bbrz.textadventure.customException.RoomNotFoundException;
import bbrz.textadventure.locations.Location;
import bbrz.textadventure.tools.CommandInterpreter;
import bbrz.textadventure.tools.OutputWrapper;
import bbrz.textadventure.tools.StringFormatter;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class Game {
    private final StringFormatter formatter = new StringFormatter();
    private final Player player;
    private Location currentLocation;
    private final OutputWrapper wrapper;
    private CommandInterpreter commandInterpreter;
    private final List<List<Location>> gameMap;
    @Setter
    private boolean loopGame = true;

    public Game(Player player, Location currentLocation, OutputWrapper wrapper) {
        this.player = player;
        this.currentLocation = currentLocation;
        this.wrapper = wrapper;
        this.gameMap = null;
    }

    public Game(Player player, OutputWrapper wrapper, List<List<Location>> gameMap) {
        this.player = player;
        this.currentLocation = gameMap.get(0).get(0);
        this.wrapper = wrapper;
        this.gameMap = gameMap;
    }

    public void printLocationItems() {
        wrapper.outPrintlnColored("\nThese are all items you can see:\n" + formatter.getPrintableStringFromItemList(currentLocation.getItems()) + "\n", TextColor.GREEN);
    }

    public void printBPItems() {
        wrapper.outPrintlnColored("\nThese are all items in your Backpack: " + formatter.getPrintableStringFromItemList(player.getStats().getBp().getBackpack()) + "\n" +
                "You have " + (player.getStats().getBp().getBACKPACK_SPACE() - player.getStats().getBp().getBackpack().size()) + " backpack slots free.\n", TextColor.GREEN);
    }

    public void addInterpreter(CommandInterpreter commandInterpreter) {
        this.commandInterpreter = commandInterpreter;
    }

    public void move(String direction) {
        try {
            currentLocation = currentLocation.getLocation(direction);
        } catch (RoomNotFoundException roomNotFound) {
            wrapper.outErr(roomNotFound.getMessage());
        }
    }

    public void printPossibleDirections() {
        var directions = currentLocation.getPointerDirections();

        for (String direction : directions) {
            wrapper.outPrintColored("\n" + direction, TextColor.GREEN);
        }
    }
}
