package bbrz.at.weatherSpringBoot.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("multi")
public class MultiController {

    @GetMapping("/pow/{value}")
    public int pow(@PathVariable int value) {
        return value * value;
    }
}
