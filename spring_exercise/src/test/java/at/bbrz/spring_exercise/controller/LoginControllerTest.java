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
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {
    public static final String BROWSER = "Browser";
    public static final String USERNAME = "username";
    public static final String PSW = "123";
    public static final long USER_ID = 13L;
    final Long CURRENT_TIME = 1L;
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

    @Captor
    private ArgumentCaptor<Player> playerCaptor;


    @BeforeEach
    void setUp() {
        loginController = new LoginController(loginChecker, systemWrapper, tokenGeneratorFactory, uuidProvider, hashProvider);
    }

    @Test
    void throwsLoginFailedExceptionWhenLoginCheckerReturnsFalse() {
        Mockito.when(loginChecker.check(loginUser)).thenReturn(false);

        var exception = assertThrows(LoginFailedException.class, ()-> loginController.login(loginUser));
        assertEquals("Login failed.", exception.getMessage());
    }

    @Test
    void throwsNoSuchAlgorithmException() throws NoSuchAlgorithmException {
        defineMockitoBehavior();
        Mockito.when(tokenGenerator.generate()).thenThrow(new NoSuchAlgorithmException("Error"));

        var exception = assertThrows(NoSuchAlgorithmException.class, ()-> loginController.login(loginUser));
        assertEquals("Error", exception.getMessage());
    }

    @Test
    void returnTokenWithCorrectValues() throws NoSuchAlgorithmException {
        defineMockitoBehavior();
        Mockito.when(tokenGenerator.generate()).thenReturn(TOKEN);
        Mockito.when(loginUser.getApplicationId()).thenReturn(BROWSER);

        Token token = loginController.login(loginUser);

        Mockito.verify(systemWrapper, Mockito.times(1)).timeStampMillis();
        assertEquals(CURRENT_TIME, token.getTimestamp());
        assertEquals(TOKEN, token.getLoginToken());
        assertEquals(BROWSER, token.getApplicationId());
    }

    @Test
    void loginUserThrowsExceptionIfEmpty() {
        var exception = assertThrows(LoginFailedException.class, ()-> loginController.login(new LoginUser()));
        assertEquals("Login failed.", exception.getMessage());
    }

    @Test
    void playerForTokenGenerator() throws NoSuchAlgorithmException {
        Mockito.when(loginChecker.check(loginUser)).thenReturn(true);
        Mockito.when(systemWrapper.timeStampMillis()).thenReturn(CURRENT_TIME);
        Mockito.when(tokenGeneratorFactory.assembleTokenGenerator(playerCaptor.capture(), Mockito.anyLong(),
                     Mockito.any(UUIDProvider.class), Mockito.any(HashProvider.class))).thenReturn(tokenGenerator);
        Mockito.when(loginUser.getUserName()).thenReturn(USERNAME);
        Mockito.when(loginUser.getPsw()).thenReturn(PSW);
        Mockito.when(loginChecker.getUserId(loginUser)).thenReturn(USER_ID);

        loginController.login(loginUser);

        assertEquals(USERNAME, playerCaptor.getValue().getName());
        assertEquals(PSW, playerCaptor.getValue().getPsw());
        assertEquals(USER_ID, playerCaptor.getValue().getId());
    }

    void defineMockitoBehavior() {
        Mockito.when(loginChecker.check(loginUser)).thenReturn(true);
        Mockito.when(systemWrapper.timeStampMillis()).thenReturn(CURRENT_TIME);
        Mockito.when(tokenGeneratorFactory.assembleTokenGenerator(Mockito.any(Player.class), Mockito.anyLong(),
                Mockito.any(UUIDProvider.class), Mockito.any(HashProvider.class))).thenReturn(tokenGenerator);
    }
}