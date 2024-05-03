package bbrz.textadventure.gameLoader;

import bbrz.textadventure.tools.OutputWrapper;
import bbrz.textadventure.tools.colors.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Scanner;

import static bbrz.textadventure.tools.colors.TextColor.GREEN;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StaticGameLoaderTest {

    StaticGameLoader gameLoader;

    @Mock
    OutputWrapper wrapper;
    @Mock
    Scanner scanner;

    @BeforeEach
    void setUp() {
        gameLoader = new StaticGameLoader();
    }

    @Test
    void initGame() {
        Mockito.when(scanner.nextLine()).thenReturn("Bob");

        var game = gameLoader.initGame(wrapper, scanner);

        Mockito.verify(wrapper, Mockito.times(1)).outPrintColored("You are:\n>", GREEN);
        assertEquals("Bob", game.getPlayer().getName());
        assertEquals(wrapper, game.getWrapper());
        assertEquals("Cottage", game.getCurrentLocation().getName());
    }
}