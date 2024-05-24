package bbrz.textadventure.entity;

import bbrz.textadventure.Game;
import bbrz.textadventure.tools.OutputWrapper;
import bbrz.textadventure.customException.NoFreeSpaceException;
import bbrz.textadventure.item.Backpack;
import bbrz.textadventure.item.Equipped;
import bbrz.textadventure.item.Item;
import bbrz.textadventure.item.ItemStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EntityTest {

    Entity entity;

    @Mock
    Item item;
    @Mock
    Item secItem;
    @Mock
    Item thirdItem;
    @Mock
    ItemStats itemStats;
    @Mock
    ItemStats secItemStats;
    @Mock
    ItemStats thirdItemStats;

    @Mock
    OutputWrapper wrapper;
    @Mock
    Game game;
    @Mock
    Backpack backpack;
    @Mock
    Equipped equipped;
    @Mock
    AttackCalc attackCalc;
    @Mock
    EntityStats stats;

    @BeforeEach
    void setUp() {
        entity = new Player("Bob", stats);
    }

    @Test
    void BPAddMultipleItems() throws NoFreeSpaceException {
        Mockito.when(stats.getBp()).thenReturn(backpack);
        entity.bpAdd(item, secItem);
        Mockito.verify(backpack, Mockito.times(1)).bpAddItems(item, secItem);
    }

    @Test
    void addOneItemToEquipped() {
        Mockito.when(stats.getEq()).thenReturn(equipped);
        entity.addEquipment(item);

        Mockito.verify(equipped, Mockito.times(1)).eqAddItems(item);
    }

    @Test
    void removeSingleItemFromEquipped() throws NoFreeSpaceException {
        Mockito.when(stats.getEq()).thenReturn(equipped);
        entity.addEquipment(item);
        entity.dropEquipment(game, item);

        Mockito.verify(equipped, Mockito.times(1)).eqAddItems(item);
        Mockito.verify(equipped, Mockito.times(1)).eqRemoveItems(game, item);

    }

    @Test
    void removeMultipleItemsFromEquipped() throws NoFreeSpaceException {
        Mockito.when(stats.getEq()).thenReturn(equipped);
        entity.addEquipment(item);
        entity.addEquipment(secItem);
        entity.dropEquipment(game, item, secItem);

        Mockito.verify(equipped, Mockito.times(1)).eqAddItems(item);
        Mockito.verify(equipped, Mockito.times(1)).eqAddItems(secItem);
        Mockito.verify(equipped, Mockito.times(1)).eqRemoveItems(game, item, secItem);
    }

    @Test
    void BPAddOneItem() throws NoFreeSpaceException {
        Mockito.when(stats.getBp()).thenReturn(backpack);
        entity.bpAdd(item);

        Mockito.verify(backpack, Mockito.times(1)).bpAddItems(item);
    }

    @Test
    void BPTryRemoveFromEmptyItems() {
        Mockito.when(stats.getBp()).thenReturn(backpack);
        entity.bpDrop(item);

        Mockito.verify(backpack, Mockito.times(1)).bpRemoveItems(item);
    }

    @Test
    void BPRemoveMultipleItems() throws NoFreeSpaceException {
        Mockito.when(stats.getBp()).thenReturn(backpack);
        entity.bpAdd(item, secItem);
        entity.bpDrop(item, secItem);

        Mockito.verify(backpack, Mockito.times(1)).bpAddItems(item, secItem);
        Mockito.verify(backpack, Mockito.times(1)).bpRemoveItems(item, secItem);
    }

    @Test
    void BPRemoveItem() throws NoFreeSpaceException {
        Mockito.when(stats.getBp()).thenReturn(backpack);
        entity.bpAdd(secItem, item);
        entity.bpDrop(secItem);

        Mockito.verify(backpack, Mockito.times(1)).bpAddItems(secItem, item);
        Mockito.verify(backpack, Mockito.times(1)).bpRemoveItems(secItem);
    }

    @Test
    void getsAttacked() {
        Mockito.when(stats.getAttackCalc()).thenReturn(attackCalc);
        Mockito.when(stats.getArmor()).thenReturn(10);
        entity.attacked(10);

        Mockito.verify(attackCalc, Mockito.times(1)).getsAttacked(10, 10);
    }

    @Test
    void getsAttackedWhereDamageShouldBeOne() {
        Mockito.when(stats.getAttackCalc()).thenReturn(attackCalc);
        Mockito.when(stats.getArmor()).thenReturn(10);
        entity.attacked(1);

        Mockito.verify(attackCalc, Mockito.times(1)).getsAttacked(1, 10);
    }

    @Test
    void getDmgRole() {
        Mockito.when(stats.getAttackCalc()).thenReturn(attackCalc);
        Mockito.when(stats.getDmg()).thenReturn(5);
        entity.roleDmg();

        Mockito.verify(attackCalc, Mockito.times(1)).getDmgRoll(5);
    }

    @Test
    void getBoostedStatsWithoutEquipment() {
        Mockito.when(stats.getHp()).thenReturn(0);
        Mockito.when(stats.getHp()).thenReturn(1);
        Mockito.when(stats.getHp()).thenReturn(2);
        Mockito.when(stats.getEq()).thenReturn(equipped);

        var list = entity.getBoostedStats();

        assertEquals(entity.getStats().getHp(), list.get(0));
        assertEquals(entity.getStats().getArmor(), list.get(1));
        assertEquals(entity.getStats().getDmg(), list.get(2));
    }

    @Test
    void getBoostedStatsWithEquipment() {
        Mockito.when(stats.getHp()).thenReturn(0);
        Mockito.when(stats.getHp()).thenReturn(1);
        Mockito.when(stats.getHp()).thenReturn(2);
        Mockito.when(stats.getEq()).thenReturn(equipped);

        Mockito.when(equipped.getEquippedList()).thenReturn(List.of(item));
        Mockito.when(item.getStats()).thenReturn(itemStats);
        Mockito.when(itemStats.getItemStats()).thenReturn(List.of(2, 3, 1));

        var list = entity.getBoostedStats();

        assertEquals(entity.getStats().getHp() + 2, list.get(0));
        assertEquals(entity.getStats().getArmor() + 3, list.get(1));
        assertEquals(entity.getStats().getDmg() + 1, list.get(2));
    }

    @Test
    void getBoostedStatsWithMultipleItemsEquipped() {
        Mockito.when(stats.getHp()).thenReturn(0);
        Mockito.when(stats.getHp()).thenReturn(1);
        Mockito.when(stats.getHp()).thenReturn(2);
        Mockito.when(stats.getEq()).thenReturn(equipped);
        Mockito.when(equipped.getEquippedList()).thenReturn(List.of(item, secItem, thirdItem));

        Mockito.when(item.getStats()).thenReturn(itemStats);
        Mockito.when(secItem.getStats()).thenReturn(secItemStats);
        Mockito.when(thirdItem.getStats()).thenReturn(thirdItemStats);

        Mockito.when(itemStats.getItemStats()).thenReturn(List.of(2, 3, 1));
        Mockito.when(secItemStats.getItemStats()).thenReturn(List.of(3, 4, 1));
        Mockito.when(thirdItemStats.getItemStats()).thenReturn(List.of(1, 0, 2));

        var list = entity.getBoostedStats();

        assertEquals(entity.getStats().getHp() + 6, list.get(0));
        assertEquals(entity.getStats().getArmor() + 7, list.get(1));
        assertEquals(entity.getStats().getDmg() + 4, list.get(2));
    }

    @Test
    void getter() {
        assertEquals("Bob", entity.getName());
        assertEquals(stats, entity.getStats());
        assertEquals(0, entity.getActualHp());
    }
}