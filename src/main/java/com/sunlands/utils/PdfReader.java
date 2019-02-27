package com.sunlands.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

/**
 * @author liujionghao
 */
public class PdfReader {
    public static String parse(File pdfFile) throws IOException {
        return parse(pdfFile, 1, Integer.MAX_VALUE);
    }

    public static String parse(File pdfFile, Integer page) throws IOException {
        return parse(pdfFile, page, page);
    }

    public static String parse(File pdfFile, Integer startPage, Integer endPage) throws IOException {
        PDDocument document = PDDocument.load(pdfFile);
        PDFTextStripper stripper = new PDFTextStripper();
        stripper.setStartPage(startPage);
        stripper.setEndPage(endPage);
        stripper.setSortByPosition(true);

        String result = stripper.getText(document);
        document.close();
        return result;
    }

    public static Integer page(File pdfFile) throws IOException {
        PDDocument document = PDDocument.load(pdfFile);
        Integer res = document.getNumberOfPages();
        document.close();
        return res;
    }
}
