package bbrz.textadventure.entity.enemys;

import bbrz.textadventure.Game;
import bbrz.textadventure.entity.AttackCalc;
import bbrz.textadventure.entity.EntityStats;
import bbrz.textadventure.item.Backpack;
import bbrz.textadventure.item.Equipped;
import bbrz.textadventure.locations.Location;

import java.util.Random;

public class Goblin extends Enemy {

    public Goblin(Game game, Location startingLocation, Random rnd) {
        super(
                "Goblin",
                new EntityStats(12, 4, 2, new Backpack(game.getWrapper()), new Equipped(game.getWrapper()), new AttackCalc(), game.getWrapper()),
                game,
                startingLocation,
                3,
                rnd
        );
    }

    @Override
    public void attackBehavior() {

    }
}
