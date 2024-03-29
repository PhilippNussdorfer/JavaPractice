package bbrz.textadventure.item;

import bbrz.textadventure.tools.OutputWrapper;
import bbrz.textadventure.customException.NoFreeSpaceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BackpackTest {

    Backpack backpack;

    @Mock
    Item item;
    @Mock
    Item secItem;
    @Mock
    OutputWrapper wrapper;

    @BeforeEach
    void setUp() {
        backpack = new Backpack(wrapper);
    }

    @Test
    void bpAddItems() throws NoFreeSpaceException {
        backpack.bpAddItems(item);

        assertEquals(1, backpack.getBackpack().size());
        assertTrue(backpack.getBackpack().contains(item));
    }

    @Test
    void addMultipleTests() throws NoFreeSpaceException {
        backpack.bpAddItems(item, secItem);

        assertEquals(2, backpack.getBackpack().size());
        assertTrue(backpack.getBackpack().contains(item));
        assertTrue(backpack.getBackpack().contains(secItem));
    }

    @Test
    void backpackIsFull() {
        assertThrows(NoFreeSpaceException.class, () -> backpack.bpAddItems(item, item, item, item, item, item, item, item, item, item, item, item, item));
    }

    @Test
    void bpRemoveItems() throws NoFreeSpaceException {
        backpack.bpAddItems(item, secItem);
        backpack.bpRemoveItems(item);

        assertTrue(backpack.getBackpack().contains(secItem));
        assertEquals(1, backpack.getBackpack().size());
    }

    @Test
    void bpRemoveMultipleItems() throws NoFreeSpaceException {
        backpack.bpAddItems(item, item, secItem);
        backpack.bpRemoveItems(item, secItem);

        assertEquals(1, backpack.getBackpack().size());
        assertTrue(backpack.getBackpack().contains(item));
    }

    @Test
    void bpItemNotFound() {
        backpack.bpRemoveItems(item);

        Mockito.verify(wrapper, Mockito.times(1)).outErr("Could not find the item: null");
    }
}