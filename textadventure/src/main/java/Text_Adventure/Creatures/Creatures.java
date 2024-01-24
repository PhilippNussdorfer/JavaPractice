package Text_Adventure.Creatures;

import Text_Adventure.Game;
import Text_Adventure.Room;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Creatures implements Serializable {

    protected Game game;
    protected Room creatureActiveRoom;
    private String creatureName;
    private String creatureDescription;
    private Room blockedRoom;
    private String blockedPath;
    private boolean canBlockPath;

    public String getCreatureName() {
        return creatureName;
    }

    protected Creatures(String creatureName, String creatureDescription, Room creatureActiveRoom, boolean canBlockPath) {
        this.creatureName = creatureName;
        this.creatureDescription = creatureDescription;
        this.creatureActiveRoom = creatureActiveRoom;
        this.canBlockPath = canBlockPath;
    }

    public void moveRoom() {
        List<String> keys = new ArrayList<>(this.creatureActiveRoom.getExits().keySet());
        int rand = (int) (Math.random() * keys.size() + 1);
        if (rand < keys.size()) {
            this.creatureActiveRoom = this.creatureActiveRoom.findExit(keys.get(rand));
        }
        if (canBlockPath) {
            rand = (int) (Math.random() * (keys.size() + 1));
            if (rand < keys.size()) {
                this.blockedRoom = this.creatureActiveRoom.findExit(keys.get(rand));
                blockedPath = keys.get(rand);
            } else {
                this.blockedRoom = null;
            }
        }
    }

    public Room getCreatureActiveRoom() {
        return this.creatureActiveRoom;
    }

    public void printCreatureDescription() {
        System.out.println(this.creatureDescription + "\n");
    }

    public void printBlockedRoom() {
        if (canBlockPath && this.blockedRoom != null) {
            System.out.println("The Path to the " + this.blockedPath + " to the " + this.blockedRoom.getName() + " is blocked by the " + this.creatureName + "\n");
        }
    }

    public Room getBlockedRoom() {
        return blockedRoom;
    }
}
