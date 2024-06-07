package at.bbrz.hello_wold;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Customer {

    private String firstname;
    private String lastname;
    private int age;
    private String CustomerNumber;
}
