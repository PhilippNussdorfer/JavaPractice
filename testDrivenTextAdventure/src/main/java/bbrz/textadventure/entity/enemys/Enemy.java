package bbrz.textadventure.entity.enemys;

import bbrz.textadventure.Game;
import bbrz.textadventure.entity.Entity;
import bbrz.textadventure.entity.EntityStats;
import bbrz.textadventure.locations.Location;

public abstract class Enemy extends Entity {

    private Location currentLocation;
    private final Location respawnPoint;
    protected final Game game;
    protected boolean isAlive;
    private int respawnCount = 20;

    public Enemy(String name, EntityStats stats, Game game, Location startingLocation) {
        super(name, stats);

        this.game = game;
        this.currentLocation = startingLocation;
        this.respawnPoint = startingLocation;
    }

    public void moveSelf() {

    }

    public void respawn() {
        if (!isAlive) {

        }
    }

    public abstract void attackBehavior();
}
