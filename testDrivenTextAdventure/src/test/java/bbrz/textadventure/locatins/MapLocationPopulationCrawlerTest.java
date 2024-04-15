package bbrz.textadventure.locatins;

import bbrz.textadventure.rules.MapRuleMark;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MapLocationPopulationCrawlerTest {

    MapLocationPopulationCrawler crawler = new MapLocationPopulationCrawler();
    List<List<Location>> map = new ArrayList<>();

    @Mock
    Location replaceable;

    @BeforeEach
    void setUp() {
        Mockito.when(replaceable.getMark()).thenReturn(MapRuleMark.REPLACEABLE);

        map.add(new ArrayList<>(List.of(replaceable, replaceable, replaceable, replaceable, replaceable, replaceable, replaceable, replaceable, null, replaceable)));
        map.add(new ArrayList<>(List.of(null, replaceable, null, null, null, null, null, replaceable, null, replaceable)));
        map.add(new ArrayList<>(List.of(null, replaceable, replaceable, replaceable, null, null, null, replaceable, null, replaceable)));
        map.add(new ArrayList<>(List.of(null, replaceable, null, replaceable, null, null, null, null, null, replaceable)));
        map.add(new ArrayList<>(List.of(replaceable, replaceable, null, replaceable, null, null, null, null, replaceable, replaceable)));
        map.add(new ArrayList<>(List.of(null, replaceable, null, replaceable, null, null, null, replaceable, replaceable, null)));
        map.add(new ArrayList<>(List.of(null, replaceable, null, replaceable, replaceable, replaceable, replaceable, replaceable, null, null)));
        map.add(new ArrayList<>(List.of(null, replaceable, null, null, null, null, null, null, null, null)));
        map.add(new ArrayList<>(List.of(null, replaceable, replaceable, replaceable, replaceable, null, null, null, null, null)));
        map.add(new ArrayList<>(List.of(null, replaceable, null, null, replaceable, replaceable, replaceable, replaceable, null, null)));
    }

    @Test
    void populateMaze() {
    }
}