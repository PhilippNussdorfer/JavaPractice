package bbrz.textadventure;

import bbrz.textadventure.customException.NoFreeSpaceException;
import bbrz.textadventure.customException.NoItemFoundException;
import bbrz.textadventure.entity.AttackCalc;
import bbrz.textadventure.entity.Player;
import bbrz.textadventure.gameLoader.MazeGenerator;
import bbrz.textadventure.gameLoader.SelfMapCompositionGameLoader;
import bbrz.textadventure.item.Backpack;
import bbrz.textadventure.item.Equipped;
import bbrz.textadventure.locatins.Location;
import bbrz.textadventure.locatins.MapLocationPopulationCrawler;
import bbrz.textadventure.rules.MapRuleMark;
import bbrz.textadventure.tools.InterpreterInit;
import bbrz.textadventure.tools.LocationPointerTool;
import bbrz.textadventure.tools.colors.TextColor;
import bbrz.textadventure.customException.CommandNotFoundException;
import bbrz.textadventure.gameLoader.StaticGameLoader;
import bbrz.textadventure.tools.Interpreter;
import bbrz.textadventure.tools.OutputWrapper;
import jdk.jshell.spi.ExecutionControl;

import java.util.Random;
import java.util.Scanner;

public class GameLoop {

    private Game game;
    private Interpreter interpreter;
    private final Scanner scanner = new Scanner(System.in);
    private final OutputWrapper wrapper = new OutputWrapper();
    private final Location replaceable = new Location("Replaceable", "for the algorithm to indicate an replaceable location", MapRuleMark.REPLACEABLE);
    private final MazeGenerator gen = new MazeGenerator(4, replaceable);
    private final MapLocationPopulationCrawler crawler = new MapLocationPopulationCrawler(InterpreterInit.initRuleInterpreter(), new Random(), new LocationPointerTool());

    public static void main(String[] args) {
        GameLoop gameLoop = new GameLoop();
        gameLoop.runGame();
    }

    private void runGame() {
        askForGameLoader();

        while (game.isLoopGame()) {
            currentInfo();
            String userInput = askForCommands();

            interpretAndHandleErrors(userInput);
        }

        if (!game.isLoopGame()) {
            wrapper.outPrintlnColored("\nGoodbye and have a nice day!", TextColor.MAGENTA);
        }
    }

    private void interpretAndHandleErrors(String userInput) {
        try {
            interpreter.interpret(userInput);
        } catch (CommandNotFoundException | ExecutionControl.NotImplementedException | IllegalArgumentException |
                 NoItemFoundException | NoFreeSpaceException exc) {
            wrapper.outErr(exc.getMessage());
        }
    }

    private String askForCommands() {
        wrapper.outPrintColored("\n\nEnter your commands:\n>", TextColor.BLUE);
        String userInput = scanner.nextLine();
        wrapper.outPrintln("");
        return userInput;
    }

    private void currentInfo() {
        wrapper.outPrintlnColored("You are at the: " + game.getCurrentLocation().getName(), TextColor.BLUE);
        game.printLocationItems();

        wrapper.outPrintlnColored("Your possible directions are: ", TextColor.BLUE);
        game.printPossibleDirections();
    }

    private void askForGameLoader() {
        wrapper.outPrintlnColored("Do you want to play a normal game or an random (yes => normal / no => random):", TextColor.GREEN);
        var input = scanner.nextLine();

        if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) {
            initGame();
        } else {
            initRandomMazeGame();
        }
    }

    private void initGame() {
        game = new StaticGameLoader().initGame(wrapper, scanner, createPlayer());
        interpreter = game.getInterpreter();
    }

    private void initRandomMazeGame() {
        game = new SelfMapCompositionGameLoader(gen, crawler).initGame(wrapper, scanner, createPlayer());
        interpreter = game.getInterpreter();
    }

    private Player createPlayer() {
        wrapper.outPrintColored("You are:\n>", TextColor.GREEN);
        String name = scanner.nextLine();

        return new Player(name, 10, 0, 2, wrapper, new AttackCalc(), new Backpack(wrapper), new Equipped(wrapper));
    }
}