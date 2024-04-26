package bbrz.textadventure.locatins;

import bbrz.textadventure.rules.MapRuleMark;
import bbrz.textadventure.rules.RuleInterpreter;
import bbrz.textadventure.tools.LocationPointerTool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MapLocationPopulationCrawlerTest {

    List<List<Location>> map = new ArrayList<>();

    @Mock
    Location replaceable;
    @Mock
    Location starting;
    @Mock
    Location meadow;
    @Mock
    Location lake;
    @Mock
    RuleInterpreter ruleInterpreter;
    @Mock
    Random random;
    @Mock
    LocationPointerTool tool;

    @BeforeEach
    void setUp() {
        Mockito.when(replaceable.getMark()).thenReturn(MapRuleMark.REPLACEABLE);
        Mockito.when(starting.getMark()).thenReturn(MapRuleMark.STARTING_LOCATION);
        Mockito.when(meadow.getMark()).thenReturn(MapRuleMark.MEADOW);
        Mockito.when(lake.getMark()).thenReturn(MapRuleMark.LAKE);

        Mockito.when(random.nextInt(3)).thenReturn(1, 1, 2, 2, 1, 1, 1, 1, 2, 1,
                                                    1, 2, 1, 2, 2, 1, 1, 1, 1, 1,
                                                    2, 1, 1, 2, 1, 2, 1, 1, 2, 1,
                                                    2, 1, 1, 2, 1, 1, 1, 2, 1, 1,
                                                    2, 1, 1, 2, 1, 1, 2);

        Mockito.when(ruleInterpreter.interpretRule(MapRuleMark.STARTING_LOCATION, MapRuleMark.MEADOW)).thenReturn(true);
        Mockito.when(ruleInterpreter.interpretRule(MapRuleMark.MEADOW, MapRuleMark.LAKE)).thenReturn(true);
        Mockito.when(ruleInterpreter.interpretRule(MapRuleMark.MEADOW, MapRuleMark.MEADOW)).thenReturn(true);
        Mockito.when(ruleInterpreter.interpretRule(MapRuleMark.LAKE, MapRuleMark.MEADOW)).thenReturn(true);
        Mockito.when(ruleInterpreter.interpretRule(MapRuleMark.LAKE, MapRuleMark.LAKE)).thenReturn(false);

        map.add(fillList(replaceable, replaceable, replaceable, replaceable, replaceable, replaceable, replaceable, replaceable, null, replaceable));
        map.add(fillList(null, replaceable, null, null, null, null, null, replaceable, null, replaceable));
        map.add(fillList(null, replaceable, replaceable, replaceable, null, null, null, replaceable, null, replaceable));
        map.add(fillList(null, replaceable, null, replaceable, null, null, null, null, null, replaceable));
        map.add(fillList(replaceable, replaceable, null, replaceable, null, null, null, null, replaceable, replaceable));
        map.add(fillList(null, replaceable, null, replaceable, null, null, null, replaceable, replaceable, null));
        map.add(fillList(null, replaceable, null, replaceable, replaceable, replaceable, replaceable, replaceable, null, null));
        map.add(fillList(null, replaceable, null, null, null, null, null, null, null, null));
        map.add(fillList(null, replaceable, replaceable, replaceable, replaceable, null, null, null, null, null));
        map.add(fillList(null, replaceable, null, null, replaceable, replaceable, replaceable, replaceable, null, null));
    }

    @Test
    void populateMaze() {
        Mockito.when(starting.cloneLocation()).thenReturn(starting);
        Mockito.when(meadow.cloneLocation()).thenReturn(meadow);
        Mockito.when(lake.cloneLocation()).thenReturn(lake);

        var crawler = new MapLocationPopulationCrawler(ruleInterpreter, random, tool);

        int countOfReplaceable = countReplaceable(map);
        var populatedMap = crawler.populateMaze(map, 0, 0, List.of(starting, meadow, lake), null);
        int countOfReplaced = countNotReplaceableAndNotNull(populatedMap);

        assertEquals(countOfReplaceable, countOfReplaced);
    }

    private int countNotReplaceableAndNotNull(List<List<Location>> map) {
        int count = 0;

        for (List<Location> row : map) {
            for (Location loc : row) {
                if (loc != replaceable && loc != null) {
                    count++;
                }
            }
        }

        return count;
    }

    private int countReplaceable(List<List<Location>> map) {
        int count = 0;

        for (List<Location> row : map) {
            for (Location loc : row) {
                if (loc == replaceable) {
                    count++;
                }
            }
        }

        return count;
    }

    private List<Location> fillList(Location ... locations) {

        return Arrays.asList(locations);
    }
}