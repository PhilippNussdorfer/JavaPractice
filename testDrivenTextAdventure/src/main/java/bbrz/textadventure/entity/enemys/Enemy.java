package bbrz.textadventure.entity.enemys;

import bbrz.textadventure.Game;
import bbrz.textadventure.customException.RoomNotFoundException;
import bbrz.textadventure.entity.Entity;
import bbrz.textadventure.entity.EntityStats;
import bbrz.textadventure.locations.Location;
import lombok.Getter;

import java.util.Random;

public abstract class Enemy extends Entity {

    @Getter
    private Location currentLocation;
    private final Location respawnPoint;
    protected final Game game;
    @Getter
    protected boolean isAlive = true;
    private int respawnCount = 20;
    private int maxFollowDistance;
    private final Random rnd;

    public Enemy(String name, EntityStats stats, Game game, Location startingLocation, int maxFollowDistance, Random rnd) {
        super(name, stats);

        this.game = game;
        this.currentLocation = startingLocation;
        this.respawnPoint = startingLocation;
        this.maxFollowDistance = maxFollowDistance;
        this.rnd = rnd;
    }

    public void moveSelf() {
        if (!isAlive)
            return;

        if (false) {


        } else {

            /*try {
                int sizeOfList = currentLocation.getPointerDirections().size();
                String direction = currentLocation.getPointerDirections().get(rnd.nextInt(sizeOfList - 1));
                Location nextLocation = currentLocation.getLocation(direction);

                currentLocation = nextLocation;
            } catch (RoomNotFoundException e) {
                System.out.println("Enemy error: " + e.getMessage());
            }*/
        }
    }

    public void respawn() {
        if (!isAlive) {
            if (respawnCount > 0) {
                respawnCount --;
            } else {
                currentLocation = respawnPoint;
                isAlive = true;
                respawnCount = 20;
            }
        }
    }

    public abstract void attackBehavior();
}
