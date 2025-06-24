package com.pgEnterprise.pgCoding.ToolsDemo.PDFCompare;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class PDFTest {

    public static boolean validateTextInPDF(String pdfPath, String expectedText) throws Exception {
            PDDocument document = PDDocument.load(new File(pdfPath));
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String pdfText = pdfStripper.getText(document);
            document.close();
            return pdfText.contains(expectedText);
        }

    public static boolean isImagePresentInPDF(String pdfPath, String expectedImagePath) throws Exception {
        PDDocument document = PDDocument.load(new File(pdfPath));
        BufferedImage expectedImage = ImageIO.read(new File(expectedImagePath));

        for (PDPage page : document.getPages()) {
            PDResources pdResources = page.getResources();

            for (COSName xObjectName : pdResources.getXObjectNames()) {
                PDXObject xObject = pdResources.getXObject(xObjectName);

                if (xObject instanceof PDImageXObject) {
                    PDImageXObject imageObject = (PDImageXObject) xObject;
                    BufferedImage extractedImage = imageObject.getImage();

                    if (compareImages(extractedImage, expectedImage)) {
                        document.close();
                        return true;
                    }
                }
            }
        }

        document.close();
        return false;
    }

    // Simple pixel-by-pixel comparison
    public static boolean compareImages(BufferedImage imgA, BufferedImage imgB) {
        int width = Math.min(imgA.getWidth(), imgB.getWidth());
        int height = Math.min(imgA.getHeight(), imgB.getHeight());

        int totalPixels = width * height;
        int matchingPixels = 0;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgbA = imgA.getRGB(x, y);
                int rgbB = imgB.getRGB(x, y);

                if (areColorsSimilar(rgbA, rgbB, 30)) { // Allow some tolerance
                    matchingPixels++;
                }
            }
        }

        double similarity = (matchingPixels * 100.0) / totalPixels;
        System.out.printf("Match: %.2f%%\n", similarity);
        return similarity >= 90.0; // Pass if 90% similar
    }

    private static boolean areColorsSimilar(int rgbA, int rgbB, int tolerance) {
        int rA = (rgbA >> 16) & 0xff;
        int gA = (rgbA >> 8) & 0xff;
        int bA = rgbA & 0xff;

        int rB = (rgbB >> 16) & 0xff;
        int gB = (rgbB >> 8) & 0xff;
        int bB = rgbB & 0xff;

        return Math.abs(rA - rB) < tolerance &&
                Math.abs(gA - gB) < tolerance &&
                Math.abs(bA - bB) < tolerance;
    }


    public static void main(String[] args) throws Exception {

        String pdfPath = System.getProperty("user.dir")+"\\pgCoding\\src\\main\\java\\Resources\\TCS logo.pdf";
        System.out.println("PDF Path " + pdfPath);
        boolean value = validateTextInPDF(pdfPath,"TCS logo");

        System.out.println("text found " + value);



        String expectedImagePath = "C:\\SeleniumProject\\pgCoding\\src\\main\\java\\Resources\\Accenture-logo.jpg";

        boolean found = isImagePresentInPDF(pdfPath, expectedImagePath);
        System.out.println("Image present in PDF? " + found);

    }

}
