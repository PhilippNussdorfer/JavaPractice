package at.bbrz.spring_exercise.controller.model;

import lombok.Getter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile("default")
public class HardcodedLoginChecker implements LoginChecker {

    private final List<DBUser> users = new ArrayList<>(List.of(
            new DBUser("Hans", "password", "Adventure", 1L),
            new DBUser("Stefan", "password123", "Browser", 2L),
            new DBUser("Christina", "psw", "Android", 3L)
    ));

    @Override
    public boolean check(LoginUser loginUser) {
        return users.contains(loginUser);
    }

    @Override
    public Long getUserId(LoginUser loginUser) {
        return users.stream()
                .filter(searchLoginUser -> searchLoginUser.equals(loginUser))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getUserId();
    }

    @Getter
    public static class DBUser extends LoginUser {
        private final Long userId;

        public DBUser(String username, String userPsw, String appId, Long userId) {
            super(username, userPsw, appId);
            this.userId = userId;
        }
    }
}
