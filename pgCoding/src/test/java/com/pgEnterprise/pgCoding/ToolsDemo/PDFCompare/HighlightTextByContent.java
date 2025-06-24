package com.pgEnterprise.pgCoding.ToolsDemo.PDFCompare;





import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.interactive.annotation.*;
import org.apache.pdfbox.text.*;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class HighlightTextByContent {

    int i=1;
    String s="hi";

    public static void main(String[] args) throws IOException {
        String searchText = "obscure Latin words, consectetur, from a Lorem "; // <-- INPUT TEXT

        String pdfPath = System.getProperty("user.dir")+"\\pgCoding\\src\\main\\java\\Resources\\Lorem.pdf";
        System.out.println("PDF Path " + pdfPath);


        PDDocument document = PDDocument.load(new File(pdfPath));
        PDFTextStripper stripper = new PDFTextStripper();

        for (int pageNum = 0; pageNum < document.getNumberOfPages(); pageNum++) {
            PDPage page = document.getPage(pageNum);

// Use custom TextStripper to get word positions
            TextPositionFinder finder = new TextPositionFinder(searchText);
            finder.setStartPage(pageNum + 1);
            finder.setEndPage(pageNum + 1);
            finder.getText(document);

            PDRectangle rect = finder.getTextRectangle();
            if (rect != null) {
                PDAnnotationTextMarkup highlight = new PDAnnotationTextMarkup(PDAnnotationTextMarkup.SUB_TYPE_HIGHLIGHT);
                highlight.setRectangle(rect);
                highlight.setQuadPoints(new float[]{
                        rect.getLowerLeftX(), rect.getUpperRightY(),
                        rect.getUpperRightX(), rect.getUpperRightY(),
                        rect.getLowerLeftX(), rect.getLowerLeftY(),
                        rect.getUpperRightX(), rect.getLowerLeftY()
                });
                highlight.setColor(new PDColor(new float[]{1, 1, 0}, PDDeviceRGB.INSTANCE));
                highlight.setConstantOpacity(0.5f);
                page.getAnnotations().add(highlight);
                break; // Stop after first match
            }
        }

        document.save("highlighted_output.pdf");
        document.close();
    }

    // Custom class to find text position
    static class TextPositionFinder extends PDFTextStripper {
        private final String searchText;
        private PDRectangle rect = null;

        public TextPositionFinder(String searchText) throws IOException {
            this.searchText = searchText;
        }

        public PDRectangle getTextRectangle() {
            return rect;
        }

        @Override
        protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
            StringBuilder line = new StringBuilder();
            for (TextPosition text : textPositions) {
                line.append(text.getUnicode());
            }

            if (line.toString().contains(searchText)) {
                TextPosition first = textPositions.get(0);
                TextPosition last = textPositions.get(textPositions.size() - 1);

                float x = first.getXDirAdj();
                float y = first.getPageHeight() - first.getYDirAdj();
                float width = last.getXDirAdj() + last.getWidth() - x;
                float height = first.getHeightDir();

                rect = new PDRectangle(x, y, width, height);
            }
        }
    }
}
