package at.bbrz.spring_exercise.token;

import at.bbrz.spring_exercise.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@Tag("UnitTest")
@Tag("Token")
class TokenGeneratorTest {
    public static final String UUID = "dba6977b-f9f2-4234-ab9f-7f3f0129b122";
    public static final String TEST_UUID = "dba6977bf9f24234ab9f7f3f0129b122";
    public static final String HASH = "0b121fa8f08165f6f30a133396b74678285762a06169b9020504d70b79f32a88";
    TokenGenerator generator;
    Long timestamp = Instant.parse("2000-01-01T00:00:00Z").toEpochMilli();
    private final Long minDate = Instant.parse("1999-12-31T23:59:59Z").toEpochMilli();

    @Mock
    Player player;
    @Mock
    UUIDProvider wrapper;
    @Mock
    HashProvider hash;

    @BeforeEach
    void setUp() {
        generator = new TokenGenerator(player, timestamp, wrapper, hash);
    }

    @Test
    void TokenNotNull() throws NoSuchAlgorithmException {
        defineGenerateBehavior();

        assertNotNull(generator.generate());
    }

    @Test
    void token96CharsLong() throws NoSuchAlgorithmException {
        defineGenerateBehavior();

        assertEquals(96, generator.generate().length());
    }

    @Test
    void tokenHasNumbersAndCharacters() throws NoSuchAlgorithmException {
        defineGenerateBehavior();

        var token = generator.generate();

        assertTrue(token.chars().anyMatch(Character::isAlphabetic));
        assertTrue(token.chars().anyMatch(Character::isDigit));
    }

    @Test
    void throwsIllegalArgumentException_whenTimestampIsBeforeYear2000() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->
            new TokenGenerator(player, 0, wrapper, hash)
        );

        assertEquals("Timestamp must be after 1999-12-31 23:59:59!", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, ()->
                new TokenGenerator(player, minDate, wrapper, hash)
        );

        assertEquals("Timestamp must be after 1999-12-31 23:59:59!", exception.getMessage());
    }

    @Test
    void throwsExceptionWhenPlayerIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->
            new TokenGenerator(null, timestamp, wrapper, hash)
        );

        assertEquals("Player is null!", exception.getMessage());
    }

    @Test
    void throwsExceptionWhenUUIDWrapperIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->
                new TokenGenerator(player, timestamp, null, hash)
        );

        assertEquals("UUIDWrapper is null!", exception.getMessage());
    }

    @Test
    void throwsExceptionWhenHashWrapperIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->
                new TokenGenerator(player, timestamp, wrapper, null)
        );

        assertEquals("HashMapper is null!", exception.getMessage());
    }

    @Test
    void uuidTokenWithoutMinus() throws NoSuchAlgorithmException {
        defineGenerateBehavior();

        assertTrue(generator.generate().contains(TEST_UUID));
    }

    @Test
    void tokenIncludesUUIDAndHash() throws NoSuchAlgorithmException {
        defineGenerateBehavior();

        assertEquals(TEST_UUID + HASH, generator.generate());
    }

    public void defineGenerateBehavior() throws NoSuchAlgorithmException {
        Mockito.when(wrapper.generateRandomUUID()).thenReturn(UUID);
        Mockito.when(hash.hashFromString(Mockito.anyString())).thenReturn(HASH);
    }
}
