package at.bbrz.spring_exercise.token;

import at.bbrz.spring_exercise.entity.Player;
import lombok.NonNull;
import org.springframework.security.crypto.codec.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.UUID;

public class TokenGenerator {
    @NonNull
    private final Player player;
    private final double timestamp;
    private final Long minDate = Instant.parse("1999-12-31T23:59:59Z").toEpochMilli();
    private final UUIDWrapper uuid;
    private final HashMapper hash;

    public TokenGenerator(Player player, double timestamp, UUIDWrapper uuid, HashMapper hash) {
        if (player == null)
            throw new IllegalArgumentException("Player is null!");
        if (timestamp <= minDate)
            throw new IllegalArgumentException("Timestamp must be after 1999-12-31 23:59:59!");
        if (uuid == null)
            throw new IllegalArgumentException("UUIDWrapper is null!");
        if (hash == null)
            throw new IllegalArgumentException("HashMapper is null!");

        this.player = player;
        this.timestamp = timestamp;
        this.uuid = uuid;
        this.hash = hash;
    }

    public String generate() throws NoSuchAlgorithmException {
        return uuid.generateRandomUUID().replace("-", "")
                + hash.hashFromString(player.getName() + timestamp + player.getId());
    }

    public static class UUIDWrapper {
        public String generateRandomUUID() {
            return UUID.randomUUID().toString();
        }
    }

    public static class HashMapper {
        public String hashFromString(String str) throws NoSuchAlgorithmException {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(str.getBytes(StandardCharsets.UTF_8));

            return new String(Hex.encode(hash));
        }
    }
}