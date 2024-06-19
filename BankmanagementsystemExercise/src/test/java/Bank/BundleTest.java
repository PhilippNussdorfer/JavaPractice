package Bank;

import Bank.commandInterpretor.Interpreter;
import Bank.person.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BundleTest {

    Bundle bundle;

    @Mock
    Session session;
    @Mock
    BankManagementSystem system;
    @Mock
    Interpreter interpreter;

    @BeforeEach
    void setUp() {
        bundle = new Bundle(session, system);
    }

    @Test
    void getter() {
        assertTrue(bundle.isRunning());

        bundle.setRunning(false);
        assertFalse(bundle.isRunning());

        assertNull(bundle.getInterpreter());
        assertEquals(session, bundle.getSession());
        assertEquals(system, bundle.getSystem());
    }

    @Test
    void setInterpreter() {
        bundle.setInterpreter(interpreter);
        assertEquals(interpreter, bundle.getInterpreter());
    }
}