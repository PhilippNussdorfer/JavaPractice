package bbrz.textadventure;

import bbrz.textadventure.customException.RoomNotFoundException;
import bbrz.textadventure.rooms.Location;
import lombok.Getter;

@Getter
public class Game {
    private final Player player;
    private Location currentLocation;

    public Game(Player player, Location currentLocation) {
        this.player = player;
        this.currentLocation = currentLocation;
    }

    public void move(String direction) {
        try {
            currentLocation = currentLocation.getRoom(direction);

        } catch (RoomNotFoundException roomNotFound) {
            System.out.println(roomNotFound.getMessage());

        }
    }


}
