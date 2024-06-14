package at.bbrz.spring_exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CryptRequest {
    private String txt;
    private String key;
}
