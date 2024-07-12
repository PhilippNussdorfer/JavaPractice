package at.bbrz.spring_exercise.controller.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class LoginUser {
    private String userName;
    private String psw;
    private String applicationId;
}
