package bbrz.textadventure;

import bbrz.textadventure.entity.Player;
import bbrz.textadventure.colors.TextColor;
import bbrz.textadventure.customException.RoomNotFoundException;
import bbrz.textadventure.item.Item;
import bbrz.textadventure.locatins.Location;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
        wrapper.outPrintlnColored("\nThese are all items in your Backpack: " + getPrintableStringFromItemList(player.getBackpack()) + "\n", TextColor.GREEN);
    }

    private String getPrintableStringFromItemList(List<Item> items) {
        StringBuilder builder = new StringBuilder();

        for (Item item : items) {
            builder.append(item.getName()).append(", ");
        }

        return builder.substring(0, builder.length() -2);
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
