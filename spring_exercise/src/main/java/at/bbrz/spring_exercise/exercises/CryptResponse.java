package at.bbrz.spring_exercise.exercises;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CryptResponse {
    private String txt;
    private String key;
}
