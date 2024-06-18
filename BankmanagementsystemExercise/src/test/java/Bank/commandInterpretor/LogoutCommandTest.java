package Bank.commandInterpretor;

import Bank.BankManagementSystem;
import Bank.Bundle;
import Bank.customExceptions.NoBundleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LogoutCommandTest {

    LogoutCommand logoutCommand = new LogoutCommand("logout");

    @Mock
    Bundle bundle;
    @Mock
    BankManagementSystem system;

    @BeforeEach
    void setUp() {
        logoutCommand.setBundle(bundle);
    }

    @Test
    void canHandle() throws NoBundleException {
        assertTrue(logoutCommand.canHandle("Logout"));
        assertFalse(logoutCommand.canHandle("exit"));
    }

    @Test
    void execute() {
        Mockito.when(bundle.getSystem()).thenReturn(system);

        logoutCommand.execute(new String[] {""});
        Mockito.verify(system, Mockito.times(1)).updateBundle(Mockito.any(Bundle.class));
    }

    @Test
    void help() {
        assertEquals("Loges the user out                                                         <Command>                                          | Commands: logout", logoutCommand.help());
    }

    @Test
    void exception() {
        logoutCommand.setBundle(null);
        assertThrows(NoBundleException.class, ()-> logoutCommand.canHandle(""));
    }
}