package bbrz.at.SpringBootTestDatabase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ZipTxtTest {

    CipherTxt cipherTxt = new CipherTxt();
    ZipTxt zip = new ZipTxt(cipherTxt);

    @BeforeEach
    void setUp() {
    }

    @Test
    void zipCode() {
        assertEquals("H8KLCAAAAAAAAMO/w4tIw43DicOJVyjDjy/DikkBAMKFEUoNCwAAAA==", zip.zipCode("hello world"));
        //with Cipher active
        //assertEquals("H8KLCAAAAAAAAMO/w4tMw4vDjS1QwqgoKMOOTQUARDZAwogLAAAA", zip.zipCode("hello world"));
    }

    @Test
    void zipCodeIsNull() {
        assertNull(zip.zipCode(""));
        assertNull(zip.zipCode("      "));
        assertNull(zip.zipCode(null));
    }

    @Test
    void unzipCode() {
        assertEquals("hello world", zip.unzipCode("H8KLCAAAAAAAAMO/w4tIw43DicOJVyjDjy/DikkBAMKFEUoNCwAAAA=="));
        //with Cipher active
        //assertEquals("hello world", zip.unzipCode("H8KLCAAAAAAAAMO/w4tMw4vDjS1QwqgoKMOOTQUARDZAwogLAAAA"));
    }

    @Test
    void unzippedCodeIsNull() {
        assertNull(zip.unzipCode(""));
        assertNull(zip.unzipCode("      "));
        assertNull(zip.unzipCode(null));
    }
}