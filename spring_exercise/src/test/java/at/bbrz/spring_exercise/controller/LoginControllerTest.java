package at.bbrz.spring_exercise.controller;

import at.bbrz.spring_exercise.controller.model.*;
import at.bbrz.spring_exercise.entity.Player;
import at.bbrz.spring_exercise.exceptions.LoginFailedException;
import at.bbrz.spring_exercise.token.HashProvider;
import at.bbrz.spring_exercise.token.TokenGenerator;
import at.bbrz.spring_exercise.token.UUIDProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {
    public static final String BROWSER = "Browser";
    final Long CURRENT_TIME = 1L;
    final String HASH = "0b121fa8f08165f6f30a133396b74678285762a06169b9020504d70b79f32a88";
    final String UUID = "dba6977bf9f24234ab9f7f3f0129b122";
    final String TOKEN = "token";
    LoginController loginController;

    @Mock
    LoginChecker loginChecker;
    @Mock
    LoginUser loginUser;
    @Mock
    SystemWrapper systemWrapper;
    @Mock
    TokenGeneratorFactory tokenGeneratorFactory;
    @Mock
    UUIDProvider uuidProvider;
    @Mock
    HashProvider hashProvider;
    @Mock
    TokenGenerator tokenGenerator;


    @BeforeEach
    void setUp() {
        loginController = new LoginController(loginChecker, systemWrapper, tokenGeneratorFactory, uuidProvider, hashProvider);
        Mockito.when(loginChecker.check(loginUser)).thenReturn(true);
    }

    @Test
    void throwsLoginFailedExceptionWhenLoginCheckerReturnsFalse() {
        Mockito.when(loginChecker.check(loginUser)).thenReturn(false);

        var exception = assertThrows(LoginFailedException.class, ()-> loginController.login(loginUser));
        assertEquals("Login failed.", exception.getMessage());
    }

    @Test
    void test() throws NoSuchAlgorithmException {
        helper();
        Mockito.when(tokenGenerator.generate()).thenReturn(TOKEN);
        Mockito.when(loginUser.getApplicationId()).thenReturn(BROWSER);

        Token token = loginController.login(loginUser);

        Mockito.verify(systemWrapper, Mockito.times(1)).timeStampMillis();
        assertEquals(CURRENT_TIME, token.getTimestamp());
        assertEquals(TOKEN, token.getLoginToken());
        assertEquals(BROWSER, token.getApplicationId());
    }

    void helper() {
        Mockito.when(systemWrapper.timeStampMillis()).thenReturn(CURRENT_TIME);
        Mockito.when(tokenGeneratorFactory.assembleTokenGenerator(Mockito.any(Player.class), Mockito.anyLong(),
                Mockito.any(UUIDProvider.class), Mockito.any(HashProvider.class))).thenReturn(tokenGenerator);
    }
}