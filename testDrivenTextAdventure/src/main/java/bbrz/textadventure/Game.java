package bbrz.textadventure;

import bbrz.textadventure.entity.Player;
import bbrz.textadventure.colors.TextColor;
import bbrz.textadventure.customException.RoomNotFoundException;
import bbrz.textadventure.item.Item;
import bbrz.textadventure.locatins.Location;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Game {
    private final Player player;
    private Location currentLocation;
    private final OutputWrapper wrapper;
    private Interpreter interpreter;
    @Setter
    private boolean loopGame = true;

    public Game(Player player, Location currentLocation, OutputWrapper wrapper) {
        this.player = player;
        this.currentLocation = currentLocation;
        this.wrapper = wrapper;
    }

    public void printLocationItems() {
        wrapper.outPrintlnColored("\nThese are all items you can see: " + getPrintableStringFromItemList(currentLocation.getItems()) + "\n", TextColor.GREEN);
    }

    public void printBPItems() {
        wrapper.outPrintlnColored("\nThese are all items in your Backpack: " + getPrintableStringFromItemList(player.getBp().getBackpack()) + "\n" +
                "You have " + (player.getBp().getBACKPACK_SPACE() - player.getBp().getBackpack().size()) + " backpack slots free.\n", TextColor.GREEN);
    }

    private String getPrintableStringFromItemList(List<Item> items) {
        return items.stream().map(Item::getName).collect(Collectors.joining(", "));
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
