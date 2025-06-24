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
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.cos.COSName;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Core;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class PDFTestAdvance {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // Load OpenCV native library
    }

    // Compare two images using SSIM
    public static double getSSIM(String path1, String path2) {
        Mat img1 = Imgcodecs.imread(path1, Imgcodecs.IMREAD_GRAYSCALE);
        Mat img2 = Imgcodecs.imread(path2, Imgcodecs.IMREAD_GRAYSCALE);

        if (img1.empty() || img2.empty()) {
            System.out.println("Error loading images.");
            return 0;
        }

        Imgproc.resize(img2, img2, img1.size()); // Resize to same size
        Mat diff = new Mat();
        Core.absdiff(img1, img2, diff);
        Core.pow(diff, 2, diff);
        Scalar mse = Core.mean(diff);
        double similarity = 100 - mse.val[0] * 100.0 / 255;
        return similarity;
    }

    public static boolean isImagePresentInPDF(String pdfPath, String expectedImagePath) throws Exception {
        PDDocument document = PDDocument.load(new File(pdfPath));
        BufferedImage expectedImage = ImageIO.read(new File(expectedImagePath));

        File tempFolder = new File("temp_extracted_images");
        tempFolder.mkdir();

        int imgCount = 0;
        boolean matched = false;

        for (PDPage page : document.getPages()) {
            PDResources resources = page.getResources();

            for (COSName xObjectName : resources.getXObjectNames()) {
                PDXObject xObject = resources.getXObject(xObjectName);

                if (xObject instanceof PDImageXObject) {
                    PDImageXObject imageObject = (PDImageXObject) xObject;
                    BufferedImage extractedImage = imageObject.getImage();

                    // Save extracted image temporarily
                    String extractedPath = "temp_extracted_images/extracted_" + imgCount + ".png";
                    ImageIO.write(extractedImage, "png", new File(extractedPath));

                    double similarity = getSSIM(extractedPath, expectedImagePath);
                    System.out.printf("Image %d similarity: %.2f%%\n", imgCount, similarity);

                    if (similarity >= 90.0) {
                        matched = true;
                        break;
                    }

                    imgCount++;
                }
            }
            if (matched) break;
        }

        document.close();
        return matched;
    }

    public static void main(String[] args) throws Exception {
        String pdfPath = System.getProperty("user.dir")+"\\pgCoding\\src\\main\\java\\Resources\\TCS logo.pdf";
        System.out.println("PDF Path " + pdfPath);

        String expectedImagePath = "C:\\SeleniumProject\\pgCoding\\src\\main\\java\\Resources\\Accenture-logo.jpg";

        boolean found = isImagePresentInPDF(pdfPath, expectedImagePath);
        System.out.println("Image found in PDF? " + (found ? "✅ YES" : "❌ NO"));
    }
}
