package at.bbrz.spring_exercise.controller;

import at.bbrz.spring_exercise.controller.model.*;
import at.bbrz.spring_exercise.entity.Player;
import at.bbrz.spring_exercise.exceptions.LoginFailedException;
import at.bbrz.spring_exercise.token.HashProvider;
import at.bbrz.spring_exercise.token.UUIDProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
public class LoginController {
    private final LoginChecker loginChecker;
    private final SystemWrapper systemWrapper;
    private final TokenGeneratorFactory tokenFactory;
    private final UUIDProvider UUID;
    private final HashProvider hash;

    public LoginController(LoginChecker loginChecker, SystemWrapper systemWrapper, TokenGeneratorFactory tokenFactory, UUIDProvider UUID, HashProvider hash) {
        this.loginChecker = loginChecker;
        this.systemWrapper = systemWrapper;
        this.tokenFactory = tokenFactory;
        this.UUID = UUID;
        this.hash = hash;
    }

    @PostMapping(path = "/login")
    public Token login(@RequestBody LoginUser loginUser) throws NoSuchAlgorithmException {
        if (!loginChecker.check(loginUser)) {
            throw new LoginFailedException("Login failed.");
        }

        var timeStamp = systemWrapper.timeStampMillis();
        var generator = tokenFactory.assembleTokenGenerator(
                            new Player(1L, loginUser.getUserName(), loginUser.getPsw()),
                            timeStamp,
                            UUID,
                hash);

        return new Token(generator.generate(), timeStamp, loginUser.getApplicationId());
    }
}
