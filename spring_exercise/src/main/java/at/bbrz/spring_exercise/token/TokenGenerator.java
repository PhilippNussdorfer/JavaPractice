package at.bbrz.spring_exercise.token;

import at.bbrz.spring_exercise.entity.Player;
import lombok.NonNull;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.UUID;

public class TokenGenerator {
    @NonNull
    private final Player player;
    private final double timestamp;
    private final UUIDProvider uuid;
    private final HashProvider hash;

    public TokenGenerator(Player player, double timestamp, UUIDProvider uuid, HashProvider hash) {
        if (player == null)
            throw new IllegalArgumentException("Player is null!");
        if (timestamp <= Instant.parse("1999-12-31T23:59:59Z").toEpochMilli())
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

    @Service
    public static class UUIDWrapper implements UUIDProvider {
        public String generateRandomUUID() {
            return UUID.randomUUID().toString();
        }
    }

    @Service
    public static class HashWrapper implements HashProvider {
        @Override
        public String hashFromString(String str) throws NoSuchAlgorithmException {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(str.getBytes(StandardCharsets.UTF_8));

            return new String(Hex.encode(hash));
        }
    }
}
