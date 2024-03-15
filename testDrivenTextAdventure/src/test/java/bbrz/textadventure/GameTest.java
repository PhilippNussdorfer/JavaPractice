package bbrz.textadventure;

import bbrz.textadventure.entity.Player;
import bbrz.textadventure.colors.TextColor;
import bbrz.textadventure.customException.RoomNotFoundException;
import bbrz.textadventure.item.Backpack;
import bbrz.textadventure.item.Item;
import bbrz.textadventure.locatins.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GameTest {

    Game game;
    @Mock
    Player player;
    @Mock
    Location location;
    @Mock
    Location secLocation;
    @Mock
    OutputWrapper wrapper;
    @Mock
    Item item;
    @Mock
    Item secItem;
    @Mock
    RoomNotFoundException exception;
    @Mock
    Interpreter interpreter;
    @Mock
    Backpack bp;

    @BeforeEach
    void setUp() {
        game = new Game(player, location, wrapper);
    }

    @Test
    void moveToNextRoom() throws RoomNotFoundException {
        Mockito.when(location.getRoom("s")).thenReturn(secLocation);
        Mockito.when(secLocation.getRoom("n")).thenReturn(location);

        game.move("s");
        assertEquals(game.getCurrentLocation(), secLocation);

        game.move("n");
        assertEquals(game.getCurrentLocation(), location);
    }

    @Test
    void getPossibleDirections() {
        Mockito.when(location.getPointerDirections()).thenReturn(List.of("s => secLocation", "n => thirdLocation"));

        game.printPossibleDirections();
        Mockito.verify(wrapper, Mockito.times(1)).outPrintColored("\ns => secLocation", TextColor.GREEN);
        Mockito.verify(wrapper, Mockito.times(1)).outPrintColored("\nn => thirdLocation", TextColor.GREEN);
    }

    @Test
    void addInterpreter() {
        game.addInterpreter(interpreter);
        assertEquals(interpreter, game.getInterpreter());
    }

    @Test
    void cantFindRoom() throws RoomNotFoundException {
        Mockito.when(location.getRoom("w")).thenThrow(exception);
        Mockito.when(exception.getMessage()).thenReturn("not found");

        game.move("w");
        Mockito.verify(wrapper, Mockito.times(1)).outErr("not found");
    }

    @Test
    void printLocationItems() {
        Mockito.when(location.getItems()).thenReturn(List.of(item));
        Mockito.when(item.getName()).thenReturn("name");

        game.printLocationItems();
        Mockito.verify(wrapper, Mockito.times(1)).outPrintlnColored("\nThese are all items you can see: name\n", TextColor.GREEN);
    }

    @Test
    void printMultipleLocationItems() {
        Mockito.when(location.getItems()).thenReturn(List.of(item, secItem));
        Mockito.when(item.getName()).thenReturn("name");
        Mockito.when(secItem.getName()).thenReturn("secName");

        game.printLocationItems();
        Mockito.verify(wrapper, Mockito.times(1)).outPrintlnColored("\nThese are all items you can see: name, secName\n", TextColor.GREEN);
    }

    @Test
    void printPlayerBPWithSingleItem() {
        Mockito.when(player.getBp()).thenReturn(bp);
        Mockito.when(bp.getBackpack()).thenReturn(List.of(item));
        Mockito.when(item.getName()).thenReturn("name");

        game.printBPItems();
        Mockito.verify(wrapper, Mockito.times(1)).outPrintlnColored("\nThese are all items in your Backpack: name\nYou have -1 backpack slots free.\n", TextColor.GREEN);
    }

    @Test
    void printPlayerBPWithMultipleItems() {
        Mockito.when(player.getBp()).thenReturn(bp);
        Mockito.when(bp.getBackpack()).thenReturn(List.of(item, secItem));
        Mockito.when(item.getName()).thenReturn("name");
        Mockito.when(secItem.getName()).thenReturn("secName");

        game.printBPItems();
        Mockito.verify(wrapper, Mockito.times(1)).outPrintlnColored("\nThese are all items in your Backpack: name, secName\nYou have -2 backpack slots free.\n", TextColor.GREEN);
    }
}