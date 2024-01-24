package bbrz.at;

import lombok.AllArgsConstructor;

import javax.print.PrintService;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

@AllArgsConstructor
public class Printer implements Printable {

    private String txt;

    public static PrintService findPrintService(String printerName) {
        PrintService[] services = PrinterJob.lookupPrintServices();
        for (PrintService service : services) {
            if (service.getName().equalsIgnoreCase(printerName)) {
                return service;
            }
        }
        return null;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2D = (Graphics2D) graphics;
        int x = (int) pageFormat.getImageableX();
        int y = (int) pageFormat.getImageableY();
        g2D.translate(x, y);

        Font font = new Font("Serif", Font.PLAIN, 16);
        FontMetrics metrics = graphics.getFontMetrics(font);
        int lineHeight = metrics.getHeight();

        BufferedReader bufferedReader = new BufferedReader(new StringReader(this.txt));

        try {
            String line;
            x += 50;
            y += 50;
            while ((line = bufferedReader.readLine()) != null) {
                y += lineHeight;
                g2D.drawString(line, x, y);
            }
        } catch (IOException i) {
            i.printStackTrace();
        }
        return PAGE_EXISTS;
    }
}
