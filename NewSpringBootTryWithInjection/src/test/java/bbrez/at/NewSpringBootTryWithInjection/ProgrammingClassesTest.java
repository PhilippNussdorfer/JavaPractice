package bbrez.at.NewSpringBootTryWithInjection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class ProgrammingClassesTest {

    private ProgrammingClasses classes;
    @Mock
    private PresenceService service;
    @Mock
    private ApplicationOutput output;
    @Mock
    private GiveGrades giveGrades;

    @BeforeEach
    void setUp() {
        Mockito.when(service.precedeAre(Mockito.anyInt())).thenReturn(List.of("Max", "Claudia", "Hans", "Bob"));
        classes = new ProgrammingClasses(service, output, giveGrades);
    }

    @Test
    void outputEightInfosToOutput() {
       classes.presenceControl(10);

       Mockito.verify(output, Mockito.times(8)).info(Mockito.anyString());
    }

    @Test
    void outputAllInfosToOutput() {
        classes.presenceControl(10);

        Mockito.verify(output, Mockito.times(1)).info("Are all here?");
        Mockito.verify(output, Mockito.times(1)).info("4 are here");
        Mockito.verify(output, Mockito.times(1)).info("Students are here.");
        Mockito.verify(output, Mockito.times(1)).info("-----------------");
        Mockito.verify(output, Mockito.times(1)).info("Max");
        Mockito.verify(output, Mockito.times(1)).info("Claudia");
        Mockito.verify(output, Mockito.times(1)).info("Hans");
        Mockito.verify(output, Mockito.times(1)).info("Bob");

    }

    @Test
    void callPrecedeAreOfPresenceService() {
        classes.presenceControl(10);

        Mockito.verify(service, Mockito.times(1)).precedeAre(10);
    }

    @Test
    void outputTests() {
        Mockito.when(giveGrades.res(Mockito.anyString())).thenReturn(new Result("Max", 2), new Result("Claudia", 3), new Result("Hans", 4), new Result("Bob", 5));
        classes.presenceControl(4);
        classes.testsNow();

        Mockito.verify(output, Mockito.times(1)).info("Max, result=2");
        Mockito.verify(output, Mockito.times(1)).info("Claudia, result=3");
        Mockito.verify(output, Mockito.times(1)).info("Hans, result=4");
        Mockito.verify(output, Mockito.times(1)).info("Bob, result=5");
    }
}