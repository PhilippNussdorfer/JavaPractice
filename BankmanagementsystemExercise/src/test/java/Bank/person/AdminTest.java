package Bank.person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

    Admin admin;

    @BeforeEach
    void setUp() {
        admin = new Admin("hans", "2.2.24", 12L, "2211", 12L);
    }

    @Test
    void getter() {
        assertEquals(12L, admin.getEmployeeID());
        assertEquals(12L, admin.getSocialNumber());
        assertEquals("hans", admin.getName());
        assertEquals("2.2.24", admin.getBirthDay());
    }

    @Test
    void login() {
        assertTrue(admin.login("2211"));
        assertFalse(admin.login("1122"));
    }
}