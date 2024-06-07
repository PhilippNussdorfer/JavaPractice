package at.bbrz.hello_wold;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping(path = "/hello")
    public String showHelloWorld() {
        return "Hello World with REST!";
    }
}
