package bbrz.textadventure.entity;

import bbrz.textadventure.OutputWrapper;

public class Player extends Entity {

    public Player(String name, int hp, int armor, int dmg, OutputWrapper wrapper) {
        super(name, hp, armor, dmg, wrapper);
    }
}
