package at.bbrz.hello_wold;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping(path = "/hello")
    public String showHelloWorld() {
        return "Hello World with REST!";
    }

    @GetMapping(path = "/customer/{name}/{lastname}")
    public Customer showCustomer(@PathVariable String name, @PathVariable String lastname) {
        return Customer.builder()
                .firstname(name)
                .lastname(lastname)
                .age(20)
                .CustomerNumber("25A9")
                .build();
    }
}
