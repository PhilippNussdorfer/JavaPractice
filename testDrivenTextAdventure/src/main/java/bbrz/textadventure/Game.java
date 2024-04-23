package bbrz.textadventure;

import bbrz.textadventure.entity.Player;
import bbrz.textadventure.tools.colors.TextColor;
import bbrz.textadventure.customException.RoomNotFoundException;
import bbrz.textadventure.locatins.Location;
import bbrz.textadventure.tools.Interpreter;
import bbrz.textadventure.tools.OutputWrapper;
import bbrz.textadventure.tools.StringFormatting;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class Game {
    private final StringFormatting formatter = new StringFormatting();
    private final Player player;
    private Location currentLocation;
    private final OutputWrapper wrapper;
    private Interpreter interpreter;
    private final List<List<Location>> gameMap;
    @Setter
    private boolean loopGame = true;

    public Game(Player player, Location currentLocation, OutputWrapper wrapper) {
        this.player = player;
        this.currentLocation = currentLocation;
        this.wrapper = wrapper;
        this.gameMap = null;
    }

    public Game(Player player, Location currentLocation, OutputWrapper wrapper, List<List<Location>> gameMap) {
        this.player = player;
        this.currentLocation = gameMap.get(0).get(0);
        this.wrapper = wrapper;
        this.gameMap = gameMap;
    }

    public void printLocationItems() {
        wrapper.outPrintlnColored("\nThese are all items you can see: " + formatter.getPrintableStringFromItemList(currentLocation.getItems()) + "\n", TextColor.GREEN);
    }

    public void printBPItems() {
        wrapper.outPrintlnColored("\nThese are all items in your Backpack: " + formatter.getPrintableStringFromItemList(player.getBp().getBackpack()) + "\n" +
                "You have " + (player.getBp().getBACKPACK_SPACE() - player.getBp().getBackpack().size()) + " backpack slots free.\n", TextColor.GREEN);
    }

    public void addInterpreter(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    public void move(String direction) {
        try {
            currentLocation = currentLocation.getRoom(direction);
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
