package at.bbrz.spring_exercise.controller.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

class HardcodedLoginCheckerTest {
    HardcodedLoginChecker loginChecker;
    LoginUser loginUser;


    @BeforeEach
    void setUp() {
        loginChecker = new HardcodedLoginChecker();
        loginUser = new LoginUser("Christina", "psw", "Android");
    }

    @Test
    void userExists() {
        assertTrue(loginChecker.check(loginUser));
    }

    @Test
    void getUserIdFor() {
        var id = loginChecker.getUserId(loginUser);

        assertEquals(3L, id);
    }
}