
package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import bbrz.textadventure.OutputWrapper;
import bbrz.textadventure.customException.CommandNotFoundException;
import bbrz.textadventure.rooms.Location;
import jdk.jshell.spi.ExecutionControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DescriptionActionTest {

    DescriptionAction action;

    @Mock
    Game game;
    @Mock
    OutputWrapper wrapper;
    @Mock
    Location location;

    @BeforeEach
    void beforeEach() {
        action = new DescriptionAction(game, wrapper, "desc", "describe", "d");
    }

    @Test
    void simpleInputs() throws ExecutionControl.NotImplementedException, CommandNotFoundException {
        Mockito.when(game.getCurrentLocation()).thenReturn(location);
        Mockito.when(location.getDescription()).thenReturn("Hello world");
        action.execute("d");
        Mockito.verify(wrapper, Mockito.times(1)).outPrintln("Hello world");

        Mockito.when(location.getDescription()).thenReturn("this is a location");
        action.execute("desc");
        Mockito.verify(wrapper, Mockito.times(1)).outPrintln("this is a location");

        Mockito.when(location.getDescription()).thenReturn("location description");
        action.execute("describe");
        Mockito.verify(wrapper, Mockito.times(1)).outPrintln("location description");
    }

    @Test
    void inputsWithAdditions() throws ExecutionControl.NotImplementedException, CommandNotFoundException {
        Mockito.when(game.getCurrentLocation()).thenReturn(location);
        Mockito.when(location.getDescription()).thenReturn("Hello world");
        action.execute("D", "Location");
        Mockito.verify(wrapper, Mockito.times(1)).outPrintln("Hello world");

        Mockito.when(location.getName()).thenReturn("Your location");
        action.execute("d", "LocationName");
        Mockito.verify(wrapper, Mockito.times(1)).outPrintln("Your location");
    }

    @Test
    void describeItems() {
        assertThrows(ExecutionControl.NotImplementedException.class, () -> action.execute("d", "item", "name"));
    }

    @Test
    void describeItemFailed() {
        assertThrows(CommandNotFoundException.class, () -> action.execute("d", "item"));
    }

    @Test
    void invalidAndValidCommands() {
        var res = action.canHandle("w");
        assertFalse(res);

        res = action.canHandle("d");
        assertTrue(res);
    }
}