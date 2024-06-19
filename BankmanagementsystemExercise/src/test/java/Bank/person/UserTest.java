package Bank.person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserTest {

    User user = new User(new ArrayList<>());

    @Mock
    Person person;
    @Mock
    Person person_2;

    @BeforeEach
    void setUp() {
    }

    @Test
    void addUser() {
        user.addUser(person);

        assertEquals(1, user.getUsers().size());

        user.addUser(person_2);

        assertEquals(2, user.getUsers().size());

        assertTrue(user.getUsers().contains(person));
        assertTrue(user.getUsers().contains(person_2));
    }
}