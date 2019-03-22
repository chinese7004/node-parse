package com.sunlands.utils;

import org.apache.poi.hslf.usermodel.*;

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
        StringBuilder content = new StringBuilder();
        for(HSLFShape shape : slide.getShapes()){
            parseShape(content, shape);
        }
        return content.toString();
    }

    private static void parseShape(StringBuilder content, HSLFShape shape) {
        if(shape instanceof HSLFTextShape){ //获取到ppt的文本信息
            for (HSLFTextParagraph paragraph : ((HSLFTextShape) shape)) {
                //获取到每一段的文本信息
                for (HSLFTextRun xslfTextRun : paragraph) {
                    content.append(xslfTextRun.getRawText());
                }
            }
        } else if (shape instanceof HSLFGroupShape) {
            for (HSLFShape childShape : ((HSLFGroupShape) shape).getShapes()) {
                parseShape(content, childShape);
            }
        }
    }

}
