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
        var foundLoginUser = users.stream().filter(searchLoginUser -> searchLoginUser.equals(loginUser)).findFirst().get();
        if (foundLoginUser instanceof DBUser)
            return ((DBUser) foundLoginUser).userId;
        else
            throw new RuntimeException("User not found.");
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
