package bbrz.at.SpringBootTestDatabase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CipherTxtTest {

    CipherTxt cipherTxt = new CipherTxt();

    @BeforeEach
    void setUp() {
    }

    @Test
    void encode() {
        String result = cipherTxt.encode("aaa");
        assertEquals("bbb", result);

        result = cipherTxt.encode("zzz");
        assertEquals("aaa", result);
    }

    @Test
    void decode() {
        String result = cipherTxt.decode("bbb");
        assertEquals("aaa", result);

        result = cipherTxt.decode("aaa");
        assertEquals("zzz", result);
    }

    @Test
    void encodeWithSpace() {
        String result = cipherTxt.encode("a aa");
        assertEquals("b bb", result);

        result = cipherTxt.encode("z zz");
        assertEquals("a aa", result);
    }

    @Test
    void decodeWithSpace() {
        String result = cipherTxt.decode("b bb");
        assertEquals("a aa", result);

        result = cipherTxt.decode("a aa");
        assertEquals("z zz", result);
    }
}