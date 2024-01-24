package bbrz.at.weatherSpringBoot.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/world")
    public String HelloWorld() {
        return "Hello World!";
    }

    @GetMapping("/world-de")
    public String HalloWelt() {
        return "Hallo Welt!";
    }
}
