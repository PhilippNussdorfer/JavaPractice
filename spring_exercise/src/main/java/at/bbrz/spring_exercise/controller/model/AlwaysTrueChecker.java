package at.bbrz.spring_exercise.controller.model;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile(("stage"))
@Service
public class AlwaysTrueChecker implements LoginChecker {

    @Override
    public boolean check(LoginUser loginUser) {
        return true;
    }

    @Override
    public int getUserId(LoginUser loginUser) {
        return 0;
    }
}
