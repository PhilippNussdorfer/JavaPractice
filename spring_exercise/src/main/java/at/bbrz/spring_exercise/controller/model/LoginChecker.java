package at.bbrz.spring_exercise.controller.model;

public interface LoginChecker {
    boolean check(LoginUser loginUser);
    int getUserId(LoginUser loginUser);
}
