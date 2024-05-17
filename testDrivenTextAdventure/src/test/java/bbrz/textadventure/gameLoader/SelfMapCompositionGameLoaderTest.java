package bbrz.textadventure.gameLoader;

import bbrz.textadventure.entity.Player;
import bbrz.textadventure.locatins.Location;
import bbrz.textadventure.locatins.MapLocationPopulationCrawler;
import bbrz.textadventure.tools.OutputWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SelfMapCompositionGameLoaderTest {

    SelfMapCompositionGameLoader gameLoader;

    @Mock
    OutputWrapper wrapper;
    @Mock
    Scanner scanner;
    @Mock
    MazeGenerator gen;
    @Mock
    Player player;
    @Mock
    MapLocationPopulationCrawler crawler;
    @Mock
    List<List<Location>> map;
    @Mock
    List<Location> row;
    @Mock
    Location location;


    @BeforeEach
    void setUp() {
        gameLoader = new SelfMapCompositionGameLoader(gen, crawler);
    }

    @Test
    void initGame() {
        Mockito.when(map.get(0)).thenReturn(row);
        Mockito.when(row.get(0)).thenReturn(location);
        Mockito.when(gen.getMazeAsList()).thenReturn(map);
        Mockito.when(crawler.populateMaze(Mockito.eq(map), Mockito.eq(0), Mockito.eq(0), Mockito.anyList(), Mockito.isNull())).thenReturn(map);

        var game = gameLoader.initGame(wrapper, scanner, player);

        assertEquals(player, game.getPlayer());
        assertEquals(wrapper, game.getWrapper());
        assertNotNull(game.getGameMap());
        assertEquals(location, game.getCurrentLocation());
    }
}