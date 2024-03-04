package bbrz.textadventure;

import bbrz.textadventure.entity.Player;
import bbrz.textadventure.colors.TextColor;
import bbrz.textadventure.customException.RoomNotFoundException;
import bbrz.textadventure.rooms.Location;
import lombok.Getter;
import lombok.Setter;

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

    public void getPossibleDirections() {
        var directions = currentLocation.getPointerDirections();

        for (String direction : directions) {
            wrapper.outPrintColored("\n" + direction, TextColor.GREEN);
        }
    }
}
