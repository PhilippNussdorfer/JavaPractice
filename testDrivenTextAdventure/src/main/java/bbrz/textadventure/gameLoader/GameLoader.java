package bbrz.textadventure.gameLoader;

import bbrz.textadventure.Game;
import bbrz.textadventure.tools.OutputWrapper;

import java.util.Scanner;

public interface GameLoader {
    Game initGame(OutputWrapper wrapper, Scanner scanner);
}
