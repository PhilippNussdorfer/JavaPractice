package at.bbrz.web_demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Person {
    private final String firstname;
    private final String lastname;
    private final int age;
}
