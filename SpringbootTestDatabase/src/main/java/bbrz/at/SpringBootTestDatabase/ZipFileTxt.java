package bbrz.at.SpringBootTestDatabase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Component
public class ZipFileTxt implements ZipFile {

    private final CipherTxt cipherTxt;
    private final WriteAndReadFile writeAndReadFile;
    private final String txtPath = "src/main/java/bbrz/at/SpringBootTestDatabase/zipHolder/zipMe.txt";
    private final String zipPath = "src/main/java/bbrz/at/SpringBootTestDatabase/zipHolder/zipMe.zip";

    @Autowired
    public ZipFileTxt(CipherTxt cipherTxt, WriteAndReadFile writeAndReadFile) {
        this.cipherTxt = cipherTxt;
        this.writeAndReadFile = writeAndReadFile;
    }

    @Override
    public String zipCodeFile(String value) {
            File fileToZip = new File(this.txtPath);
            writeAndReadFile.write(fileToZip, cipherTxt.encode(value));

        try {
            FileOutputStream fos = new FileOutputStream(this.zipPath);
            ZipOutputStream zipOut = new ZipOutputStream(fos);

            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[4096];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }

            zipOut.close();
            fis.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File not Found!");
        }
        return cipherTxt.encode(value);
    }

    @Override
    public String unzipCodeFile() {
        File destDir = new File("src/main/java/bbrz/at/SpringBootTestDatabase/unzipTarget");
        byte[] buffer = new byte[4096];

        try {
            ZipInputStream zis = new ZipInputStream(new FileInputStream(this.zipPath));
            ZipEntry zipEntry = zis.getNextEntry();

            while (zipEntry != null) {
                File newFile = newFile(destDir, zipEntry);

                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Failed to create directory " + newFile);
                    }
                }

                else {
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }

                    FileOutputStream fos = new FileOutputStream(newFile);
                    int length;
                    while ((length = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, length);
                    }
                    fos.close();
                }
                zipEntry = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();
            String[] strArr = this.txtPath.split("/");
            return this.cipherTxt.decode(writeAndReadFile.read(new File(destDir + "/" + strArr[strArr.length - 1])));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File not found!");
        }
        return null;
    }

    private File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }
}
