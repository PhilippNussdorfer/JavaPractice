package bbrz.at.SpringBootTestDatabase.rest;

import bbrz.at.SpringBootTestDatabase.Cipher;
import bbrz.at.SpringBootTestDatabase.ZipFile;
import bbrz.at.SpringBootTestDatabase.ZipTxt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/cipher")
public class CipherController {

    @Autowired
    private Cipher cipher;
    @Autowired
    private ZipFile zipFile;
    @Autowired
    private ZipTxt zip;

    @PostMapping("/encode")
    public String encode(@RequestBody String value) {
        return cipher.encode(value);
    }

    @PostMapping("/decode")
    public String decode(@RequestBody String value) {
        return cipher.decode(value);
    }

    @PostMapping("/zipFile")
    public String zipCodeFile(@RequestBody String value) {
        return zipFile.zipCodeFile(value);
    }

    @PostMapping("/unzipFile")
    public String unzipCodeFile() {
        return zipFile.unzipCodeFile();
    }

    @PostMapping("/zip")
    public String zipCode(@RequestBody String value) {
        return zip.zipCode(value);
    }

    @PostMapping("/unzip")
    public String unzipCode(@RequestBody String value) {
        return zip.unzipCode(value);
    }
}
