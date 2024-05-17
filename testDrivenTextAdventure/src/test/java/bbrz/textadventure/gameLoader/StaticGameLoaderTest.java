package bbrz.textadventure.gameLoader;

import bbrz.textadventure.entity.Player;
import bbrz.textadventure.tools.OutputWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StaticGameLoaderTest {

    StaticGameLoader gameLoader;

    @Mock
    OutputWrapper wrapper;
    @Mock
    Scanner scanner;
    @Mock
    Player player;

    @BeforeEach
    void setUp() {
        gameLoader = new StaticGameLoader();
    }

    @Test
    void initGame() {
        var game = gameLoader.initGame(wrapper, scanner, player);

        assertEquals(player, game.getPlayer());
        assertEquals(wrapper, game.getWrapper());
        assertEquals("Cottage", game.getCurrentLocation().getName());
        assertNull(game.getGameMap());
    }
}