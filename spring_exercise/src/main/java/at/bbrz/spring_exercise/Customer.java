package at.bbrz.spring_exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Customer {
    private String firstname;
    private String lastname;
    private String customerNumber;
    private int age;
}
