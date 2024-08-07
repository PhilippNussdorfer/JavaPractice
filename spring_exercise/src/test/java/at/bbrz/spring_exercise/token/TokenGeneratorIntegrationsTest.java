package at.bbrz.spring_exercise.token;

import at.bbrz.spring_exercise.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

@Tag("IntegrationsTest")
class TokenGeneratorIntegrationsTest {

    TokenGenerator tokenGenerator;

    @BeforeEach
    void setUp() {
        tokenGenerator = new TokenGenerator(
                new Player(1L, "Bob", "HelloWorld"),
                System.currentTimeMillis(),
                new TokenGenerator.UUIDWrapper(),
                new TokenGenerator.HashWrapper());
    }

    @Test
    void generate() throws NoSuchAlgorithmException {
        var token = tokenGenerator.generate();

        assertEquals(96, token.length());
    }

    @Test
    void tokenHasNumbersAndCharacters() throws NoSuchAlgorithmException {
        var token = tokenGenerator.generate();

        assertTrue(token.chars().anyMatch(Character::isAlphabetic));
        assertTrue(token.chars().anyMatch(Character::isDigit));
        assertTrue(token.chars().allMatch(Character::isLetterOrDigit));
    }

    @Test
    void tokenShouldNotEqual() throws NoSuchAlgorithmException {
        var token1 = tokenGenerator.generate();
        var token2 = tokenGenerator.generate();

        assertNotEquals(token1, token2);
    }

    @Test
    void hashValuesAreDifferentForNewTokenGenerator() throws NoSuchAlgorithmException, InterruptedException {
        var generator = generateToken();
        var token1 = generator.generate();

        Thread.sleep(1);
        generator = generateToken();
        var token2 = generator.generate();

        assertNotEquals(token1.substring(32), token2.substring(32));
    }

    @Test
    void playerNameCanBeNull() throws NoSuchAlgorithmException {
        var generator = new TokenGenerator(
                new Player(2L, null, "Osswald"),
                System.currentTimeMillis(),
                new TokenGenerator.UUIDWrapper(),
                new TokenGenerator.HashWrapper());

        assertNotNull(generator.generate());
    }

    private TokenGenerator generateToken() {
        return new TokenGenerator(
                new Player(2L, "Harry", "Osswald"),
                System.currentTimeMillis(),
                new TokenGenerator.UUIDWrapper(),
                new TokenGenerator.HashWrapper());
    }
}