package com.sunlands.model;

import java.util.List;

public class DocumentVerifyResult {
    private Integer count = 0;
    private Integer total = 0;
    private List<KnowledgeNode> missKnowledgeNodeList;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<KnowledgeNode> getMissKnowledgeNodeList() {
        return missKnowledgeNodeList;
    }

    public void setMissKnowledgeNodeList(List<KnowledgeNode> missKnowledgeNodeList) {
        this.missKnowledgeNodeList = missKnowledgeNodeList;
    }
}
