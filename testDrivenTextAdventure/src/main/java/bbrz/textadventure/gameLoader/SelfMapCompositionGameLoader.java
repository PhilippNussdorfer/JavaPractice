package bbrz.textadventure.gameLoader;

import bbrz.textadventure.Game;
import bbrz.textadventure.rules.*;
import bbrz.textadventure.tools.InterpreterInit;
import bbrz.textadventure.entity.Player;
import bbrz.textadventure.locations.Location;
import bbrz.textadventure.locations.MapLocationPopulationCrawler;
import bbrz.textadventure.tools.OutputWrapper;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
public class SelfMapCompositionGameLoader implements GameLoader {
    private MazeGenerator gen;
    private MapLocationPopulationCrawler crawler;

    private List<Location> initLocations() {
        return List.of(
                new Location("Cottage", "It's a small cottage where you can hear the wind howling through the cracks in the old windows and a small fireplace in the middle of the room where a fire is lit.", MapRuleMark.STARTING_LOCATION),
                new Location("Well", "A Well with clear Water.", MapRuleMark.WELL),
                new Location("Woods", "This dark and eerie where you can here the howling of the wind and owls through this dark night.", MapRuleMark.WOODS),
                new Location("Clearing", "A small clearing in the woods with a big rock in the middle surrounded with firefly's", MapRuleMark.CLEARING),
                new Location("Cliff", "On top on the cliff, when you look down you can see the beach and the sea where the moon reflects.", MapRuleMark.CLIFF),
                new Location("Beach", "On this sand beach where the sea slowly caresses the beach.", MapRuleMark.BEACH),
                new Location("Lake", "It's a small lake separated from the sea and where the clear blue water that reflects the moon.", MapRuleMark.LAKE)
        );
    }

    @Override
    public Game initGame(OutputWrapper wrapper, Scanner scanner, Player player) {
        var locations = initLocations();

        gen.generateMaze();
        var map = gen.getMazeAsList();
        map = crawler.populateMaze(map, 0, 0, locations, null);

        Game game = new Game(player, wrapper, map);
        game.addInterpreter(InterpreterInit.initActionInterpreter(game, wrapper));

        return game;
    }
}
