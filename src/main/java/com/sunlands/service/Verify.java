package com.sunlands.service;

import com.alibaba.fastjson.JSON;
import com.sunlands.model.DocumentVerifyResult;
import com.sunlands.model.KnowledgeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Verify {
    private static final Logger logger = LoggerFactory.getLogger(Verify.class);

    public static DocumentVerifyResult verify(String path, List<KnowledgeNode> knowledgeNodeList) {
        logger.info("verify start path=" + path + ",knowledgeNodeList=" + knowledgeNodeList);

        DocumentVerifyResult res = new DocumentVerifyResult();
        if (knowledgeNodeList == null || knowledgeNodeList.size() == 0) {
            return res;
        }
        res.setTotal(knowledgeNodeList.size());

        Map<Integer, List<KnowledgeNode>> parseResult = Parse.parse(path, knowledgeNodeList);
        logger.info(JSON.toJSONString(parseResult));

        List<KnowledgeNode> tempList = new ArrayList<>(knowledgeNodeList);
        if (parseResult != null && parseResult.size() > 0) {
            for (Integer page : parseResult.keySet()) {
                List<KnowledgeNode> nodes = parseResult.get(page);
                for (KnowledgeNode node : nodes) {
                    for (KnowledgeNode temp : tempList) {
                        if (node.getId().equals(temp.getId())) {
                            temp.setId(0);
                            break;
                        }
                    }
                }
            }
        }

        List<KnowledgeNode> missKnowledgeNodeList = new ArrayList<>();
        Integer count = 0;
        for (KnowledgeNode temp : tempList) {
            if (temp.getId().equals(0)) {
                count++;
            } else {
                missKnowledgeNodeList.add(temp);
            }
        }

        res.setCount(count);
        res.setMissKnowledgeNodeList(missKnowledgeNodeList);
        logger.info("匹配结果=" + JSON.toJSONString(res));
        logger.info("verify end======================================");
        return res;
    }

    public static void main(String[] argv) {
        String path = "C:\\Users\\Huoshan\\Desktop\\eg1\\碎片化验证课件\\941492.pptx";
        List<KnowledgeNode> knowledgeNodeList = new ArrayList<>();
        KnowledgeNode knowledgeNode = new KnowledgeNode();
        knowledgeNode.setId(1);
        knowledgeNode.setName("货币转化为资本和劳动力成为商品");
        knowledgeNode.setSerialNumber("4.2.1.1");
        knowledgeNodeList.add(knowledgeNode);

        DocumentVerifyResult res = verify(path, knowledgeNodeList);
    }
}
