package bbrz.at.SpringBootTestDatabase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@Component
public class ZipTxt implements Zip {

    private final CipherTxt cipherTxt;

    @Autowired
    public ZipTxt(CipherTxt cipherTxt) {
        this.cipherTxt = cipherTxt;
    }


    @Override
    public String zipCode(String value) {
        if (value == null || value.trim().length() == 0) {
            return null;
        }

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(out);

            //adds the cipher
            //value = cipherTxt.encode(value);
            gzip.write(value.getBytes());
            gzip.close();

            String zipped = out.toString(StandardCharsets.ISO_8859_1);
            return Base64.getEncoder().encodeToString(zipped.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String unzipCode(String value) {
        if (value == null || value.trim().length() == 0) {
            return null;
        }

        try {
            byte[] decodedBase = Base64.getDecoder().decode(value.getBytes());
            String zip = new String(decodedBase);

            GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(zip.getBytes(StandardCharsets.ISO_8859_1)));
            BufferedReader bfr = new BufferedReader(new InputStreamReader(gis, StandardCharsets.ISO_8859_1));
            String out = "", line;

            while ((line = bfr.readLine()) != null) {
                out += line;
            }

            //used for, when the cipher is added
            //out = cipherTxt.decode(out);
            return out;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
