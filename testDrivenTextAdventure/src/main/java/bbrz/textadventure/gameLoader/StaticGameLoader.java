package bbrz.textadventure.gameLoader;

import bbrz.textadventure.Game;
import bbrz.textadventure.tools.InterpreterInit;
import bbrz.textadventure.tools.colors.TextColor;
import bbrz.textadventure.entity.AttackCalc;
import bbrz.textadventure.entity.Player;
import bbrz.textadventure.item.*;
import bbrz.textadventure.locatins.Location;
import bbrz.textadventure.locatins.LocationPointer;
import bbrz.textadventure.rules.MapRuleMark;
import bbrz.textadventure.tools.OutputWrapper;

import java.util.Scanner;

public class StaticGameLoader implements GameLoader {

    public Game initGame(OutputWrapper wrapper, Scanner scanner) {
        Location cottage = initLocationsAndGetStartingLocation();

        wrapper.outPrintColored("You are:\n>", TextColor.GREEN);
        String name = scanner.nextLine();

        Game game = new Game(new Player(name, 10, 0, 2, wrapper, new AttackCalc(), new Backpack(wrapper), new Equipped(wrapper)), cottage, wrapper);
        game.addInterpreter(InterpreterInit.initActionInterpreter(game, wrapper));

        return game;
    }

    private Location initLocationsAndGetStartingLocation() {
        Item candle = Item.builder().type(ItemType.CONSUMABLE).description("When lit the world around you will be illuminated").stats(new ItemStats()).name("Candle").value(10).build();
        Item matches = Item.builder().name("Matches").description("Can start a fire.").value(6).stats(new ItemStats()).type(ItemType.CONSUMABLE).build();
        Item oldIronHelmet = Item.builder().name("Old-Iron-Helmet").description("It's olb butt still protects.").value(15).stats(new ItemStats(3, 5, 0)).type(ItemType.HELMET).build();
        Item oldBronzeSword = Item.builder().name("Old-Bronze-Sword").description("when sharpened and repaired than it cold be like new").value(11).type(ItemType.WEAPON).stats(new ItemStats(0, 0, 3)).build();

        Location cottage = new Location("Cottage", "It's a small cottage where you can hear the wind howling through the cracks in the old windows and a small fireplace in the middle of the room where a fire is lit.", MapRuleMark.STARTING_LOCATION);
        Location well = new Location("Well", "A Well with clear Water.", MapRuleMark.WELL);
        Location woods = new Location("Woods", "This dark and eerie where you can here the howling of the wind and owls through this dark night.", MapRuleMark.WOODS);
        Location clearing = new Location("Clearing", "A small clearing in the woods with a big rock in the middle surrounded with firefly's", MapRuleMark.CLEARING);
        Location cliff = new Location("Cliff", "On top on the cliff, when you look down you can see the beach and the sea where the moon reflects.", MapRuleMark.CLIFF);
        Location beach = new Location("Beach", "On this sand beach where the sea slowly caresses the beach.", MapRuleMark.BEACH);
        Location lake = new Location("Lake", "It's a small lake separated from the sea and where the clear blue water that reflects the moon.", MapRuleMark.LAKE);

        cottage.addItems(candle, matches);
        cottage.addPointers(new LocationPointer("s", well));

        well.addItems(oldIronHelmet);
        well.addPointers(new LocationPointer("n", cottage),
                new LocationPointer("s", woods),
                new LocationPointer("w", cliff));

        cliff.addItems(oldBronzeSword);
        cliff.addPointers(new LocationPointer("e", well),
                new LocationPointer("s", beach));

        woods.addPointers(new LocationPointer("n", well),
                new LocationPointer("e", clearing),
                new LocationPointer("s", lake));

        beach.addPointers(new LocationPointer("n", cliff),
                new LocationPointer("w", lake));

        clearing.addPointers(new LocationPointer("w", woods));

        lake.addPointers(new LocationPointer("n", woods),
                new LocationPointer("e", beach));

        return cottage;
    }
}
