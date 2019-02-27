package com.sunlands.utils;

import org.apache.poi.xslf.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PptxReader {
    public static String read(String filePath) {
        StringBuilder content = new StringBuilder();
        InputStream istream = null;
        try {
            istream = new FileInputStream(filePath);
            XMLSlideShow ppt = new XMLSlideShow(istream);
            //遍历每一页ppt
            for(XSLFSlide slide : ppt.getSlides()){
                content.append(parseSlide(slide)).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (istream!=null) {
                try {
                    istream.close();
                } catch (IOException e) {
//                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

    public static List<XSLFSlide> readPages(String filePath) {
        List<XSLFSlide> slide = null;
        InputStream istream = null;
        try {
            istream = new FileInputStream(filePath);
            XMLSlideShow ppt = new XMLSlideShow(istream);
            slide = ppt.getSlides();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (istream!=null) {
                try {
                    istream.close();
                } catch (IOException e) {
//                    e.printStackTrace();
                }
            }
        }
        return slide;
    }

    public static String parseSlide(XSLFSlide slide) {
        StringBuilder content = new StringBuilder();
        for(XSLFShape shape:slide.getShapes()){
            if(shape instanceof XSLFTextShape){ //获取到ppt的文本信息
                for (XSLFTextParagraph paragraph : ((XSLFTextShape) shape)) {
                    //获取到每一段的文本信息
                    for (XSLFTextRun xslfTextRun : paragraph) {
                        content.append(xslfTextRun.getRawText());
                    }
                }
            }
        }
        return content.toString();
    }
}
