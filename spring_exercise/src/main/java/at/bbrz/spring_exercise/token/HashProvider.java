package at.bbrz.spring_exercise.token;

import java.security.NoSuchAlgorithmException;

public interface HashProvider {
    String hashFromString(String str) throws NoSuchAlgorithmException;
}
