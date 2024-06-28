package at.bbrz.spring_exercise.exercises;

import org.springframework.stereotype.Service;

@Service
public class CryptService {
    public String xor(String key, String input) {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            output.append((char) (input.charAt(i) ^ key.charAt(i % key.length())+31));
        }

        return output.toString();
    }
}
