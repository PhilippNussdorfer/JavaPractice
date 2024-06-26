package Bank.person;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SessionTest {

    Session session;

    @Mock
    Person person;

    @Test
    void getUser() {
        session  = new Session(person);

        assertEquals(person, session.getUser());
    }
}