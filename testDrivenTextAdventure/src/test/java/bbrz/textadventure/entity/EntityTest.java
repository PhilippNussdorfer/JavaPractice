package bbrz.textadventure.entity;

import bbrz.textadventure.OutputWrapper;
import bbrz.textadventure.customException.NoFreeSpaceException;
import bbrz.textadventure.item.Item;
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
    OutputWrapper wrapper;

    @BeforeEach
    void setUp() {
        entity = new Player("Bob", 10, 10, 5, wrapper);
    }

    @Test
    void BPAddMultipleItems() throws NoFreeSpaceException {
        entity.BPAddItems(item, secItem);

        assertTrue(entity.getBackpack().containsAll(List.of(item, secItem)));
        assertEquals(2, entity.getBackpack().size());
    }

    @Test
    void BPAddOneItem() throws NoFreeSpaceException {
        entity.BPAddItems(item);

        assertTrue(entity.getBackpack().contains(item));
        assertEquals(1, entity.getBackpack().size());
    }

    @Test
    void BPAddedToMatch() {
        assertThrows(NoFreeSpaceException.class, ()-> entity.BPAddItems(item, item, item, item, item, item, item, item, item, item, item, item, item));
    }

    @Test
    void BPTryRemoveFromEmptyItems() {
        entity.BPRemoveItems(item);
        Mockito.verify(wrapper, Mockito.times(1)).outErr("Could not find the item: null");
    }

    @Test
    void BPRemoveMultipleItems() throws NoFreeSpaceException {
        entity.BPAddItems(item, secItem);
        entity.BPRemoveItems(item, secItem);

        assertEquals(0, entity.getBackpack().size());
    }

    @Test
    void BPRemoveItem() throws NoFreeSpaceException {
        entity.BPAddItems(secItem, item);
        entity.BPRemoveItems(secItem);

        assertEquals(1, entity.getBackpack().size());
    }

    @Test
    void getsAttacked() {
        entity.getsAttacked(10);

        assertEquals(1, entity.getHp());
    }

    @Test
    void getDmgRole() {
        for (int i = 0; i < 10; i++) {
            var role = entity.getDmgRole();

            System.out.println(role);
            assertTrue(List.of(5, 4, 3).contains(role));
        }
    }
}