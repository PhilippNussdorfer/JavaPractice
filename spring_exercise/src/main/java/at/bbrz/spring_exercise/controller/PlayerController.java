package at.bbrz.spring_exercise.controller;

import at.bbrz.spring_exercise.entity.Player;
import at.bbrz.spring_exercise.repo.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    PlayerRepository repo;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping(value = "/add")
    public void addPlayer(@RequestBody Player player) {
        player.setPsw(passwordEncoder.encode(player.getPsw()));
        repo.save(player);
    }
}
