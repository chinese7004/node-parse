package com.sunlands.service;

import com.sunlands.model.KnowledgeNode;
import com.sunlands.utils.PdfReader;
import com.sunlands.utils.PptReader;
import com.sunlands.utils.PptxReader;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import java.io.File;
import java.util.*;

public class Parse {
    private static final int ONE_PAGE_NODE_MAX_NUMBER = 4;

    public static Map<Integer, List<KnowledgeNode>> parse(String path, List<KnowledgeNode> knowledgeNodeList) {
        Map<Integer, List<KnowledgeNode>> res = new HashMap<>();
        if (knowledgeNodeList == null || knowledgeNodeList.size() == 0) {
            return res;
        }

        String suffix = calSuffix(path);
        List<String> contents = new ArrayList<>();
        try {
            if (".pptx".equals(suffix)) {
                List<XSLFSlide> slides = PptxReader.readPages(path);
                if (slides != null) {
                    for (XSLFSlide slide : slides) {
                        String content = PptxReader.parseSlide(slide);
                        contents.add(content);
                    }
                }
            } else if (".ppt".equals(suffix)) {
                List slides = PptReader.readPages(path);
                for (Object slide : slides) {
                    String content = PptReader.parseSlide((HSLFSlide) slide);
                    content = content.replace("\n", "");
                    contents.add(content);
                }
            } else if (suffix.contains(".pdf")) {
                File pdfFile = new File(path);
                Integer size = PdfReader.page(pdfFile);
                for (int i = 0; i < size; i++) {
                    String content = PdfReader.parse(pdfFile, i + 1);
                    contents.add(content);
                }
            }
        } catch (Exception e) {
            return res;
        }

        Integer index = 1;
        for (String content : contents) {
            if (content == null || "".equals(content) || content.contains("练一练") || content.contains("练习")) {
                continue;
            }

            List<KnowledgeNode> temp = new ArrayList<>();
            for (KnowledgeNode knowledgeNode : knowledgeNodeList) {
                if (hasSerialNumber(knowledgeNode, content)) {
                    temp.add(knowledgeNode);
                }
            }

            if (temp.size() > 0 && temp.size() < ONE_PAGE_NODE_MAX_NUMBER) {
                res.put(index++, temp);
            }
        }

        return res;
    }

    public static void main(String[] argv) {
        String path = "C:\\Users\\Huoshan\\Desktop\\末级知识点全部.ppt";
        List<KnowledgeNode> knowledgeNodeList = new ArrayList<>();
        KnowledgeNode knowledgeNode = new KnowledgeNode();
        knowledgeNode.setId(208108);
        knowledgeNode.setName("二元一次方程组");
        knowledgeNode.setSerialNumber("1.1.1.2");
        knowledgeNodeList.add(knowledgeNode);

        Map<Integer, List<KnowledgeNode>> res = parse(path, knowledgeNodeList);
        for (Integer page : res.keySet()) {
            List<KnowledgeNode> nodes = res.get(page);
            System.out.println("page " + page);
            for (KnowledgeNode item : nodes) {
                System.out.println(item.getName());
            }
        }
    }

    public static String calSuffix(String url) {
        if (url.contains(".pptx")) {
            return ".pptx";
        } else if (url.contains(".ppt")) {
            return ".ppt";
        } else {
            return ".pdf";
        }
    }

    /**
     * 判断是否埋了知识点序号
     * @param knowledgeNode
     * @param content
     * @return
     */
    private static Boolean hasSerialNumber(KnowledgeNode knowledgeNode, String content) {
        String serialNumber = knowledgeNode.getSerialNumber();
        String name = knowledgeNode.getName();

        String flagStr = serialNumber + ":" + name;
        String flagStr2 = serialNumber + ":" + splitName(name);
        String flagStr3 = serialNumber + "：" + name;
        String flagStr4 = serialNumber + "：" + splitName(name);
        String flagStr5 = serialNumber + " " + name;
        String flagStr6 = serialNumber + " " + splitName(name);
        String flagStr7 = serialNumber + " " + name;
        String flagStr8 = serialNumber + " " + splitName(name);
        String flagStr9 = serialNumber + name;
        String flagStr10 = serialNumber + splitName(name);

        return content.contains(flagStr) || content.contains(flagStr2)
                || content.contains(flagStr3) || content.contains(flagStr4)
                || content.contains(flagStr5) || content.contains(flagStr6)
                || content.contains(flagStr7) || content.contains(flagStr8)
                || content.contains(flagStr9) || content.contains(flagStr10);
    }

    /**
     * 去掉前缀的（一、）或第一章
     * @param knowledgeNodeName
     * @return
     */
    private static String splitName(String knowledgeNodeName) {
        List<String> splits = new ArrayList<>();
        splits.add(" ");
        splits.add("、");

        String name = knowledgeNodeName;
        for (String split : splits) {
            Integer first = name.indexOf(split);
            if (first != -1) {
                name = name.substring(first + 1, name.length());
            }
        }

        return name;
    }
}
