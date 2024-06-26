package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import bbrz.textadventure.entity.EntityStats;
import bbrz.textadventure.tools.StringFormatter;
import bbrz.textadventure.tools.colors.TextColor;
import bbrz.textadventure.entity.Player;
import bbrz.textadventure.item.Equipped;
import bbrz.textadventure.item.Item;
import bbrz.textadventure.tools.OutputWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PlayerInfoActionTest {

    PlayerInfoAction action;

    @Mock
    Game game;
    @Mock
    Player player;
    @Mock
    Equipped equipped;
    @Mock
    Item item;
    @Mock
    Item secItem;
    @Mock
    OutputWrapper wrapper;
    @Mock
    EntityStats stats;
    @Mock
    StringFormatter formatter;

    @BeforeEach
    void setUp() {
        action = new PlayerInfoAction(game, "info", "pi");
    }

    @Test
    void execute() {
        Mockito.when(game.getPlayer()).thenReturn(player);
        Mockito.when(player.getName()).thenReturn("Hans");
        Mockito.when(player.getStats()).thenReturn(stats);
        Mockito.when(stats.getEq()).thenReturn(equipped);
        Mockito.when(equipped.getEquippedList()).thenReturn(List.of(item, secItem));
        Mockito.when(equipped.getEQUIPPED_SPACE()).thenReturn(8);
        Mockito.when(game.getWrapper()).thenReturn(wrapper);
        Mockito.when(player.getBoostedStats()).thenReturn(List.of(15, 20, 9));
        Mockito.when(player.getActualHp()).thenReturn(10);
        Mockito.when(game.getFormatter()).thenReturn(formatter);
        Mockito.when(formatter.getPrintableStringFromItemList(List.of(item, secItem))).thenReturn("Iron Helmet, Rusty old Sword");

        action.execute("pi");
        Mockito.verify(wrapper, Mockito.times(2)).outPrintlnColored("=".repeat(210), TextColor.DARK_BROWN);
        Mockito.verify(wrapper, Mockito.times(1)).outPrintlnColored("""
                You are: Hans

                HP: 15/10
                Armor: 20
                Dmg: 9

                Your equipment that you have equipped is:
                Iron Helmet, Rusty old Sword
                You have 6 equipment slots free.""", TextColor.MAGENTA);
    }

    @Test
    void reacts() {
        assertTrue(action.canHandle("info"));
        assertTrue(action.canHandle("PI"));
        assertFalse(action.canHandle("help"));
    }

    @Test
    void helpMessage() {
        Mockito.when(game.getFormatter()).thenReturn(formatter);
        Mockito.when(formatter.getPrintableCollection(Mockito.any(String[].class))).thenReturn("info, pi                      ");
        Mockito.when(formatter.formatStringLength(Mockito.anyInt(), Mockito.anyString())).thenReturn("Player info    ", "Shows information about the player and his equipment <Command>                                      ", "info, pi                      ");

        assertEquals("Player info     => Shows information about the player and his equipment <Command>                                       | Commands => info, pi                      ", action.helpMessage());
    }
}