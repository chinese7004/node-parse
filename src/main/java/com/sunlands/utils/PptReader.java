package com.sunlands.utils;

import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFTextParagraph;
import org.apache.poi.hslf.usermodel.HSLFTextRun;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PptReader {
    public static List readPages(String filePath) throws IOException{
        InputStream istream = new FileInputStream(filePath);
        HSLFSlideShow ss= new HSLFSlideShow(istream);
        return ss.getSlides();

    }

    public static String parseSlide(HSLFSlide slide) {
        StringBuilder sb = new StringBuilder();
        List<List<HSLFTextParagraph>> textParagraphss = slide.getTextParagraphs();
        for (List<HSLFTextParagraph> textParagraphs : textParagraphss) {
            for (HSLFTextParagraph textParagraph : textParagraphs) {
                List<HSLFTextRun> textRuns = textParagraph.getTextRuns();
                for (HSLFTextRun run : textRuns) {
                    sb.append(run.getRawText()).append("\n");
                }
            }
        }
        return sb.toString();
    }

}
