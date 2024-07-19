package at.bbrz.spring_exercise.controller.model;

import lombok.Getter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile("default")
public class HardcodedLoginChecker implements LoginChecker {

    private final List<LoginUser> users = new ArrayList<>(List.of(
            new DBUser("Hans", "password", "Adventure", 1),
            new DBUser("Stefan", "password123", "Browser", 2),
            new DBUser("Christina", "psw", "Android", 3)
    ));

    @Override
    public boolean check(LoginUser loginUser) {
        return users.contains(loginUser);
    }

    @Override
    public int getUserId(LoginUser loginUser) {
        return 0;
    }

    @Getter
    private static class DBUser extends LoginUser {
        private int userId;

        public DBUser(String username, String userPsw, String appId, int userId) {
            super(username, userPsw, appId);
            this.userId = userId;
        }
    }
}
