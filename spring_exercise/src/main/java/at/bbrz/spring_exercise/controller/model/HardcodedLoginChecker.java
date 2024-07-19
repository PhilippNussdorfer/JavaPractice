package at.bbrz.spring_exercise.controller.model;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile("default")
public class HardcodedLoginChecker implements LoginChecker {

    private final List<LoginUser> users = new ArrayList<>(List.of(
            new LoginUser("Hans", "password", "Adventure"),
            new LoginUser("Stefan", "password123", "Browser"),
            new LoginUser("Christina", "psw", "Android")
    ));

    @Override
    public boolean check(LoginUser loginUser) {
        return users.contains(loginUser);
    }
}
