

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Buddhi on 3/12/2017.
 */
public class testPdfbox {

    public static void main(String args[]) {

        try {
            PDDocument pddDocument = PDDocument.load((new URL("https://supreme.justia.com/cases/federal/us/580/15-606/case.pdf")).openStream());
            PDFTextStripper textStripper = new PDFTextStripper();
            String doc = textStripper.getText(pddDocument);
            pddDocument.close();
            System.out.println(doc);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }



        /*

        PDFTextStripper pdfStripper = null;
        PDDocument pdDoc = null;
        COSDocument cosDoc = null;
        File file = new File("C:\\Users\\Buddhi\\IdeaProjects\\WebCrawler\\components\\justia\\src\\test\\java\\case.pdf");
        try {
            PDFParser parser = new PDFParser(new RandomAccessBufferedFileInputStream(file));
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            String parsedText = pdfStripper.getText(pdDoc);
            System.out.println(parsedText);
        } catch (IOException e) {
//            e.printStackTrace();
        }
*/


    }
}
