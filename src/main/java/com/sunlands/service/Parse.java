package com.sunlands.service;

import com.alibaba.fastjson.JSON;
import com.sunlands.model.KnowledgeNode;
import com.sunlands.utils.PdfReader;
import com.sunlands.utils.PptReader;
import com.sunlands.utils.PptxReader;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

public class Parse {
    private static final Logger logger = LoggerFactory.getLogger(Parse.class);

    private static final int ONE_PAGE_NODE_MAX_NUMBER = 4;

    public static Map<Integer, List<KnowledgeNode>> parse(String path, List<KnowledgeNode> knowledgeNodeList) throws Exception {
        logger.info("parse start path=" + path + ",knowledgeNodeList=" + knowledgeNodeList.toString());

        Map<Integer, List<KnowledgeNode>> res = new HashMap<>();
        if (knowledgeNodeList == null || knowledgeNodeList.size() == 0) {
            logger.info("knowledgeNodeList is empty. parse end=============================");
            return res;
        }

        String suffix = calSuffix(path);
        List<String> contents = new ArrayList<>();
        if (".pptx".equals(suffix)) {
            List<XSLFSlide> slides = PptxReader.readPages(path);
            if (slides != null) {
                for (XSLFSlide slide : slides) {
                    String content = PptxReader.parseSlide(slide);
                    content = replaceSpace(content);
                    contents.add(content);
                }
            }
        } else if (".ppt".equals(suffix)) {
            List slides = PptReader.readPages(path);
            for (Object slide : slides) {
                String content = PptReader.parseSlide((HSLFSlide) slide);
                content = content.replace("\n", "");
                content = replaceSpace(content);
                contents.add(content);
            }
        } else if (suffix.contains(".pdf")) {
            File pdfFile = new File(path);
            Integer size = PdfReader.page(pdfFile);
            for (int i = 0; i < size; i++) {
                String content = PdfReader.parse(pdfFile, i + 1);
                content = replaceSpace(content);
                contents.add(content);
            }
        }

        logger.info("contents=" + String.valueOf(contents));

        Integer index = 0;
        for (String content : contents) {
            index++;
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
                res.put(index, temp);
            }
        }

        logger.info("parse end======================================");
        return res;
    }

    private static String replaceSpace(String content) {
        content = content.replace(" ", "");
        content = content.replace(" ", "");
        return content;
    }

    public static void main(String[] argv) throws Exception {
        String pptx = "C:\\Users\\Huoshan\\Desktop\\1.pptx";
        List<KnowledgeNode> knowledgeNodeList = new ArrayList<>();

        KnowledgeNode knowledgeNode = new KnowledgeNode();
        knowledgeNode.setId(107662);
        knowledgeNode.setName("科学毁了我的晚餐（Science Has Spoiled My Supper）");
        knowledgeNode.setSerialNumber("1.1.2.8");
        knowledgeNodeList.add(knowledgeNode);

        show(pptx, knowledgeNodeList);
    }

    private static void show(String path, List<KnowledgeNode> knowledgeNodeList) throws Exception {
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
        url = url.toLowerCase();
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
        content = content.toLowerCase();
        String serialNumber = knowledgeNode.getSerialNumber().toLowerCase();
        String name = knowledgeNode.getName().toLowerCase();

        List<String> flagStrs = new ArrayList<>();
        flagStrs.add(replaceSpace(serialNumber + ":" + name));
        flagStrs.add(replaceSpace(serialNumber + ":" + splitName(name)));
        flagStrs.add(replaceSpace(serialNumber + "：" + name));
        flagStrs.add(replaceSpace(serialNumber + "：" + splitName(name)));
        flagStrs.add(replaceSpace(serialNumber + name));
        flagStrs.add(replaceSpace(serialNumber + splitName(name)));

        for (String flagStr : flagStrs) {
            if (content.contains(flagStr)) {
                return true;
            }
        }
        return false;
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
