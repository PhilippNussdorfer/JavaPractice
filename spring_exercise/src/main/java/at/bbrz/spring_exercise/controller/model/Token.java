package at.bbrz.spring_exercise.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Token {
    private String loginToken;
    private Long Timestamp;
    private String applicationId;
}
