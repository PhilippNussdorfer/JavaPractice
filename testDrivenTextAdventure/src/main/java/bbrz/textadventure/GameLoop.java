package bbrz.textadventure;

import bbrz.textadventure.customException.NoFreeSpaceException;
import bbrz.textadventure.customException.NoItemFoundException;
import bbrz.textadventure.colors.TextColor;
import bbrz.textadventure.customException.CommandNotFoundException;
import bbrz.textadventure.gameLoader.StaticGameLoader;
import bbrz.textadventure.tools.Interpreter;
import bbrz.textadventure.tools.OutputWrapper;
import jdk.jshell.spi.ExecutionControl;

import java.util.Scanner;

public class GameLoop {

    private Game game;
    private Interpreter interpreter;
    private final Scanner scanner = new Scanner(System.in);
    private final OutputWrapper wrapper = new OutputWrapper();

    public static void main(String[] args) {
        GameLoop gameLoop = new GameLoop();
        gameLoop.runGame();
    }

    private void runGame() {
        initGame();

        while (game.isLoopGame()) {
            wrapper.outPrintlnColored("You are at the: " + game.getCurrentLocation().getName(), TextColor.BLUE);
            game.printLocationItems();
            wrapper.outPrintlnColored("Your possible directions are: ", TextColor.BLUE);
            game.printPossibleDirections();

            wrapper.outPrintColored("\n\nEnter your commands:\n>", TextColor.BLUE);
            String userInput = scanner.nextLine();

            try {
                interpreter.interpret(userInput);
            } catch (CommandNotFoundException | ExecutionControl.NotImplementedException | IllegalArgumentException |
                     NoItemFoundException | NoFreeSpaceException exc) {
                wrapper.outErr(exc.getMessage());
            }
        }

        if (!game.isLoopGame()) {
            wrapper.outPrintlnColored("\nGoodbye and have a nice day!", TextColor.MAGENTA);
        }
    }

    private void initGame() {
        game = new StaticGameLoader().initGame(wrapper, scanner);
        interpreter = game.getInterpreter();
    }
}