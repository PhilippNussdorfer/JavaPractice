package at.bbrz.spring_exercise.exercises;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Customer {
    private String firstname;
    private String lastname;
    private String customerNumber;
    private int age;
}
