package bbrz.textadventure.entity;

import bbrz.textadventure.OutputWrapper;
import bbrz.textadventure.item.Backpack;
import bbrz.textadventure.item.Equipped;

public class Player extends Entity {

    public Player(String name, int hp, int armor, int dmg, OutputWrapper wrapper, AttackCalc attackCalc, Backpack bp, Equipped eq) {
        super(name, hp, armor, dmg, wrapper, attackCalc, bp, eq);
    }
}