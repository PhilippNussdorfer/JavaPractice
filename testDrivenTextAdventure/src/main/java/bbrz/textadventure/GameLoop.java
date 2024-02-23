package bbrz.textadventure;

import bbrz.textadventure.actions.DescriptionAction;
import bbrz.textadventure.actions.MoveAction;
import bbrz.textadventure.actions.ExitAction;
import bbrz.textadventure.customException.CommandNotFoundException;
import bbrz.textadventure.rooms.Location;
import bbrz.textadventure.rooms.LocationPointer;
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

        boolean runGame = true;

        while (game.isLoopGame()) {
            wrapper.outPrintln("You are at the: " + game.getCurrentLocation().getName());
            wrapper.outPrint("Enter your commands:\n>");
            String userInput = scanner.nextLine();

            try {
                interpreter.interpret(userInput);
            } catch (CommandNotFoundException | ExecutionControl.NotImplementedException exc) {
                wrapper.outPrintln(exc.getMessage());
            }
        }
    }

    private void initGame() {
        Location cottage = new Location("Cottage", "It's a small cottage where you can hear the wind howling through the cracks in the old windows and a small fireplace in the middle of the room where a fire is lit.");
        Location well = new Location("Well", "A Well with clear Water.");
        Location woods = new Location("Woods", "This dark and eerie where you can here the howling of the wind and owls through this dark night.");
        Location clearing = new Location("Clearing", "A small clearing in the woods with a big rock in the middle surrounded with firefly's");
        Location cliff = new Location("Cliff", "On top on the cliff, when you look down you can see the beach and the sea where the moon reflects.");
        Location beach = new Location("Beach", "On this sand beach where the sea slowly caresses the beach.");
        Location lake = new Location("Lake", "It's a small lake separated from the sea and where the clear blue water that reflects the moon.");

        cottage.addPointers(new LocationPointer("s", well));

        well.addPointers(new LocationPointer("n", cottage),
                new LocationPointer("s", woods),
                new LocationPointer("w", cliff));

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

        game = new Game(new Player(), cottage);

        interpreter = new Interpreter();
        interpreter.addActions(new MoveAction(game , "west", "north", "east", "south", "n", "s", "w", "e"));
        interpreter.addActions(new DescriptionAction(game, wrapper, "d", "desc", "describe"));
        interpreter.addActions(new ExitAction(game, "ex", "x", "exit"));
    }
}