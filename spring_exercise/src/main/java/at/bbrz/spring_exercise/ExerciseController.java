package at.bbrz.spring_exercise;

import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log
public class ExerciseController {

    @GetMapping(path = "/hello")
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping(path = "/customer")
    public Customer customer() {

        Customer customer = new Customer("Helga", "Beham", "1222456", 65);

        log.info("Created customer: " + customer);
        return customer;
    }

    @GetMapping(path = "/customer/{firstname}/{lastname}/{customerNumber}/{age}")
    public Customer customer(@PathVariable String firstname, @PathVariable String lastname, @PathVariable String customerNumber, @PathVariable int age) {
        return new Customer(firstname, lastname, customerNumber, age);
    }

    @GetMapping(path = "/customer_2")
    public Customer customer_sec(@RequestParam String firstname, @RequestParam String lastname, @RequestParam String customerNumber, @RequestParam int age) {
        return new Customer(firstname, lastname, customerNumber, age);
    }
}
