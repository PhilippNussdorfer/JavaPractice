package at.bbrz.spring_exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CryptResponse {
    private String txt;
    private String key;
}
