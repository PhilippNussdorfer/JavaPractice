package Text_Adventure;

import Text_Adventure.Action.CommandInterpreter;
import Text_Adventure.Creatures.Boar;
import Text_Adventure.Creatures.Buck;
import Text_Adventure.Creatures.Creatures;
import Text_Adventure.Printer.ANSIPrinter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game implements Serializable {

    private Room activeRoom;
    private Player activePlayer;
    private CommandInterpreter commandInterpreter;
    private ItemMap allItemMap;
    private List<Creatures> allCreaturesList;

    public Game() {init();}

    private void init() {
        activePlayer=new Player("default");
        commandInterpreter = new CommandInterpreter(activePlayer, this);
        allItemMap = new ItemMap();
        allCreaturesList = new ArrayList<>();

        Room cottage = new Room("Inside of a cottage",
                "It is a small room With a warm fire place in The middle of the room.",
                new ANSIPrinter("Cottage","00140242019600000190002700940000000002360000000000950000000000000248" +
                        "000000000004000400000000000800080000000000040004000000000000000B000B000B000B000B000B000B000B000B000B000B000B" +
                        "00000000000B000B000B000B000B000F000F000B000B000B000B000B00000004000B000B0005000B000F00030001000F000B0005000B" +
                        "000B00040004000B000B0005000B000F00010001000F000B0005000B000B00040000000B000B000B000B000B000F000F000B000B000B" +
                        "000B000B00000000000B000B000B000B000B000B000B000B000B000B000B000B000000000000000400040000000000000000000000000004000400000000"));
        Room outsideOfTheCottage = new Room("Outside of the cottage",
                "You see a vast Forest, it is night and you can see a bright full moon how it shines down on to" +
                         "the cottage you look around and you see a small and dark path in to the forest, its barely enough light from the moon to see.");
        Room darkForestPath = new Room("A small path in a dark forest.",
                "The forest seems to eat away all light of the moon and you can hear the wind howling through the forest.");
        Room aCrossPathInTheForest = new Room("A Cross Path",
                "you stand before an cross path in the forest you can scarcely see an sign post, in one direction it says 'Well', but the sign for the other direction is to damaged to read and the Path is to Dark to go without a light.");
        Room theWell = new Room("The well in the clearing", "you come out of the forest to a clearing in the middle of it stands an old well but it seems to be usable if you can find a bucket and a rope." +
                " You can see hundreds of firefly's around the well.");

        cottage.addItem(allItemMap.getItem("dirtyL"), allItemMap.getItem("mat1"), allItemMap.getItem("mat2"), allItemMap.getItem("fireP"));
        outsideOfTheCottage.addItem(allItemMap.getItem("axe"), allItemMap.getItem("bucket"));
        theWell.addItem(allItemMap.getItem("spo"), allItemMap.getItem("well"), allItemMap.getItem("holeG"));
        darkForestPath.addItem(allItemMap.getItem("rope"));

        cottage.addRoom("N", outsideOfTheCottage);
        outsideOfTheCottage.addRoom("S", cottage);
        outsideOfTheCottage.addRoom("W", darkForestPath);
        darkForestPath.addRoom("N", theWell);
        darkForestPath.addRoom("O", outsideOfTheCottage);
        theWell.addRoom("S", darkForestPath);

        Buck strangeBuck = new Buck(darkForestPath);
        Boar giantBoar = new Boar(outsideOfTheCottage);

        allCreaturesList.add(strangeBuck);
        allCreaturesList.add(giantBoar);

        activeRoom = cottage;
    }

    public Item getItemFromItemMap(String itemKey) {return allItemMap.getItem(itemKey);}
    public Room getActiveRoom() {return activeRoom;}
    public List<Creatures> getAllCreaturesList() {return allCreaturesList;}

    public void run() {

        /*ANSIPrinter pacMan = new ANSIPrinter("pac", "08080000112500000000000000000000000000000000000000000011110001111110111121111111100011110000111111000111111000111100");
        pacMan.printImport();
        ANSIPrinter mage = new ANSIPrinter("mage", "191B239040091015094224000000000000000000000000000000666666660000006666666600666666666601111110666666000066666666011111111066660066006666660111111111106600666600666660222222222206600666600666600222222222200660066006666011111111111111066660066666600454552252500666660666666660454523323206666660666666660455523323206666660666666666055052252506666600666666666605500555066666020666666000010555550000000202066660111111000001111112202066601111111112211111112202066602200011121120000000000666602220011111210666666660666602220011112110666666660666660006011111110666666660666666666011112110666666660666666660111101111066666660666666002111060111200666660666600222210666012222006660666022222220666022222220606066000000000666000000000660666");
        mage.printImport();*/
        Scanner scan = new Scanner(System.in);
        userInputForName(scan);

        while (true) {

            System.out.println("You are here: " + activeRoom.getName() + "\n");
            activeRoom.printRoomIMG();
            System.out.println(activeRoom.getDescription() + "\n");

            ifCreatureInRoomPrint();
            itemsInRoomAndRoomDirections();
            moveRoomAndMoveCreatures(scan);
        }
    }

    public void initGame(Game loadGame) {
        this.activeRoom = loadGame.activeRoom;
        this.activePlayer = loadGame.activePlayer;
        this.allCreaturesList = loadGame.allCreaturesList;
    }

    private boolean blocksPath(Room foundRoom) {
        for (Creatures creature : allCreaturesList) {
            if (creature.getCreatureActiveRoom() == this.activeRoom) {
                if (creature.getBlockedRoom() == foundRoom) {
                    System.out.println("You can't go in this direction a " + creature.getCreatureName() + " blocks the path.\n");
                    return false;
                }
            }
        }
        return true;
    }

    private void moveRoomAndMoveCreatures(Scanner scan) {
        Room foundRoom;
        String input = scan.nextLine();
        foundRoom = activeRoom.findExit(input);

        if (foundRoom != null && blocksPath(foundRoom)) {
            activeRoom = foundRoom;
            for (Creatures creature : allCreaturesList) {
                creature.moveRoom();
            }
        } else {
            commandInterpreter.interpret(input);
        }
    }

    private void itemsInRoomAndRoomDirections() {
        System.out.println("You can see those items in the vicinity: ");
        if (activeRoom.getItemsInTheRoom() != null) {
            for (Item name : activeRoom.getItemsInTheRoom()) {
                System.out.print(name.getName() + "   ");
            }
        }
        System.out.println("\n");
        System.out.println(activeRoom.getDirection());
        System.out.print("> ");
    }

    private void ifCreatureInRoomPrint() {
        for (Creatures creature : allCreaturesList) {
            if (creature.getCreatureActiveRoom() == activeRoom) {
                creature.printCreatureDescription();
                creature.printBlockedRoom();
            }
        }
    }

    private void userInputForName(Scanner scan) {

        while (true) {
            System.out.println("Who are you? Asks a friendly but cryptic voice.\n");
            System.out.print("> ");
            String str = scan.nextLine();
            if (!str.equals("")) {
                activePlayer.setName(str);
                System.out.println("Welcome " + activePlayer.getName() + " to this wonderful world of mine! says the voice and fades away.");
                System.out.println("\n");
                break;
            } else {
                System.out.println("It repeats it self.\n");
            }
        }
    }
}
