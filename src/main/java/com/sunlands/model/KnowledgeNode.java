package com.sunlands.model;

import java.util.Date;
import java.util.Objects;

public class KnowledgeNode implements Comparable<KnowledgeNode>{
    private Integer id;
    private Integer knowledgeTreeId;
    private String serialNumber;
    private String name;
    private String description;
    private Integer level;
    private Integer parentNodeId;
    private String outlineRequirement; // code
    private byte invalidFlag;
    private Date createTime;
    private Date updateTime;
    private String creator;
    private byte deleteFlag;
    private String operator;
    private Integer subjectId;

    public KnowledgeNode(){}
    public KnowledgeNode(Integer id, Integer knowledgeTreeId, String serialNumber, String name,
                         String description, Integer level, Integer parentNodeId, String outlineRequirement, String creator, String operator){
        this.id = id;
        this.knowledgeTreeId = knowledgeTreeId;
        this.serialNumber = serialNumber;
        this.name = name;
        this.description = description;
        this.level = level;
        this.parentNodeId = parentNodeId;
        this.outlineRequirement = outlineRequirement;
        this.creator = creator;
        this.operator = operator;
        this.invalidFlag = invalidFlag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKnowledgeTreeId() {
        return knowledgeTreeId;
    }

    public void setKnowledgeTreeId(Integer knowledgeTreeId) {
        this.knowledgeTreeId = knowledgeTreeId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getParentNodeId() {
        return parentNodeId;
    }

    public void setParentNodeId(Integer parentNodeId) {
        this.parentNodeId = parentNodeId;
    }

    public String getOutlineRequirement() {
        return outlineRequirement;
    }

    public void setOutlineRequirement(String outlineRequirement) {
        this.outlineRequirement = outlineRequirement;
    }

    public byte getInvalidFlag() {
        return invalidFlag;
    }

    public void setInvalidFlag(byte invalidFlag) {
        this.invalidFlag = invalidFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public byte getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(byte deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public int compareTo(KnowledgeNode o) {
        return this.serialNumber.compareTo(o.serialNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KnowledgeNode that = (KnowledgeNode) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
