package com.ecms.bean;

import java.util.List;
import java.util.Map;

public class ProductStructOptionRelationInfo {
    //页面传值
    private String optionId;
    private String optionIndex;
    private List<Map<String, String>> subList;

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getOptionIndex() {
        return optionIndex;
    }

    public void setOptionIndex(String optionIndex) {
        this.optionIndex = optionIndex;
    }

    public List<Map<String, String>> getSubList() {
        return subList;
    }

    public void setSubList(List<Map<String, String>> subList) {
        this.subList = subList;
    }
}