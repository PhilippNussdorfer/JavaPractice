package bbrz.at.weatherSpringBoot.rest;

import bbrz.at.weatherSpringBoot.model.Temperature;
import bbrz.at.weatherSpringBoot.repo.TemperatureRepo;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/temperature")
@Log
public class TemperatureController {

    @Autowired
    private TemperatureRepo repo;

    @GetMapping("/a/{value}")
    public Temperature getATemperature(@PathVariable double value) {
        return new Temperature(1, "Kapfenberg", System.currentTimeMillis(), value);
    }

    @PostMapping("/add")
    public void addTemperature(@RequestBody Temperature temperature) {
        log.info("I wanted to add " + temperature);
        repo.save(temperature);
    }
}
