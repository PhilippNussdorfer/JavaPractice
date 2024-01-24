package bbrz.adventure.game.AnimationAreaDirection;

import bbrz.adventure.game.InputRegister.Direction;
import bbrz.adventure.game.InputRegister.InputStatus;
import com.almasb.fxgl.entity.Entity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class InterpretAreaDirectionTest {

    InterpretAreaDirection interpretAreaDirection;
    @Mock
    InputStatus inputStatus;
    @Mock
    UpAreaDirection upAreaDirection;
    @Mock
    DownAreaDirection downAreaDirection;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        interpretAreaDirection = new InterpretAreaDirection(inputStatus, List.of(upAreaDirection, downAreaDirection));
    }

    @Test
    void interpretDirection() {
        Mockito.when(inputStatus.isIdle()).thenReturn(true);
        Mockito.when(inputStatus.getLastKnownDirection()).thenReturn(Direction.UP);
        Mockito.when(upAreaDirection.getIndicator()).thenReturn(Direction.UP);
        interpretAreaDirection.interpret();
        Mockito.verify(upAreaDirection, Mockito.times(1)).setAttackArea();
    }

    @Test
    void TestWithTwoItemsListInInputStatus() {
        Mockito.when(inputStatus.isIdle()).thenReturn(false);
        Mockito.when(inputStatus.getDirectionList()).thenReturn(List.of(Direction.UP, Direction.DOWN));
        Mockito.when(downAreaDirection.getIndicator()).thenReturn(Direction.DOWN);

        interpretAreaDirection.interpret();
        Mockito.verify(downAreaDirection, Mockito.times(1)).setAttackArea();
    }

    @Test
    void TestWithOnlyOneInputInInputStatusList() {
        Mockito.when(inputStatus.isIdle()).thenReturn(false);
        Mockito.when(inputStatus.getDirectionList()).thenReturn(List.of(Direction.UP));
        Mockito.when(upAreaDirection.getIndicator()).thenReturn(Direction.UP);

        interpretAreaDirection.interpret();
        Mockito.verify(upAreaDirection, Mockito.times(1)).setAttackArea();
    }
}