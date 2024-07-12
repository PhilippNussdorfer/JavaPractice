package at.bbrz.spring_exercise.controller.model;

import at.bbrz.spring_exercise.entity.Player;
import at.bbrz.spring_exercise.token.HashProvider;
import at.bbrz.spring_exercise.token.TokenGenerator;
import at.bbrz.spring_exercise.token.UUIDProvider;
import org.springframework.stereotype.Service;

@Service
public class TokenGeneratorFactory {

    public TokenGenerator assembleTokenGenerator(Player player, Long timeStamp, UUIDProvider UUID, HashProvider hash) {
        return new TokenGenerator(player, timeStamp, UUID, hash);
    }
}
