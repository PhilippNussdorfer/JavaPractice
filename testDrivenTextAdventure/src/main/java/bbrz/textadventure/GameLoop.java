package bbrz.textadventure;

import bbrz.textadventure.actions.*;
import bbrz.textadventure.customException.NoFreeSpaceException;
import bbrz.textadventure.customException.NoItemFoundException;
import bbrz.textadventure.entity.AttackCalc;
import bbrz.textadventure.entity.Player;
import bbrz.textadventure.colors.TextColor;
import bbrz.textadventure.customException.CommandNotFoundException;
import bbrz.textadventure.item.*;
import bbrz.textadventure.locatins.Location;
import bbrz.textadventure.locatins.LocationPointer;
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
        Item candle = Item.builder().type(ItemType.CONSUMABLE).description("When lit the world around you will be illuminated").stats(new ItemStats()).name("Candle").value(10).build();
        Item matches = Item.builder().name("Matches").description("Can start a fire.").value(6).stats(new ItemStats()).type(ItemType.CONSUMABLE).build();
        Item oldIronHelmet = Item.builder().name("Old Iron Helmet").description("It's olb butt still protects.").value(15).stats(new ItemStats(3, 5, 0)).type(ItemType.HELMET).build();
        Item oldBronzeSword = Item.builder().name("Old Bronze Sword").description("when sharpened and repaired than it cold be like new").value(11).type(ItemType.WEAPON).stats(new ItemStats(0, 0, 3)).build();

        Location cottage = new Location("Cottage", "It's a small cottage where you can hear the wind howling through the cracks in the old windows and a small fireplace in the middle of the room where a fire is lit.");
        Location well = new Location("Well", "A Well with clear Water.");
        Location woods = new Location("Woods", "This dark and eerie where you can here the howling of the wind and owls through this dark night.");
        Location clearing = new Location("Clearing", "A small clearing in the woods with a big rock in the middle surrounded with firefly's");
        Location cliff = new Location("Cliff", "On top on the cliff, when you look down you can see the beach and the sea where the moon reflects.");
        Location beach = new Location("Beach", "On this sand beach where the sea slowly caresses the beach.");
        Location lake = new Location("Lake", "It's a small lake separated from the sea and where the clear blue water that reflects the moon.");

        cottage.addItems(candle, matches);
        cottage.addPointers(new LocationPointer("s", well));

        well.addItems(oldIronHelmet);
        well.addPointers(new LocationPointer("n", cottage),
                new LocationPointer("s", woods),
                new LocationPointer("w", cliff));

        cliff.addItems(oldBronzeSword);
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

        wrapper.outPrintColored("You are:\n>", TextColor.GREEN);
        String name = scanner.nextLine();

        game = new Game(new Player(name, 10, 0, 2, wrapper, new AttackCalc(), new Backpack(this.wrapper), new Equipped(this.wrapper)), cottage, wrapper);

        interpreter = new Interpreter();
        interpreter.addActions(new HelpAction(game, "h", "help"));
        interpreter.addActions(new MoveAction(game , "west", "north", "east", "south", "n", "s", "w", "e"));
        interpreter.addActions(new ExitAction(game, "ex", "x", "exit"));
        interpreter.addActions(new DescriptionAction(game, wrapper, "d", "desc", "describe"));
        interpreter.addActions(new PickUpAction(game, "pickup", "pick"));
        interpreter.addActions(new DropAction(game, "drop"));
        interpreter.addActions(new BackpackAction(game, "bp", "backpack"));

        game.addInterpreter(interpreter);
    }
}