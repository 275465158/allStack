package com.ecms.bean;

import java.util.List;

public class ProductStructOptionInfo extends BaseCompanyAndStatusInfo {

    //选择器id
    private Integer selectorId;

    //所属选择器
    private ProductStructSelectorInfo selectorInfo;

    //选项值
    private String optionValue;

    //排序
    private Integer orderBy;

    //页面状态字
    private String type;

    private List<ProductStructRelationInfo> relationInfos;

    public Integer getSelectorId() {
        return selectorId;
    }

    public void setSelectorId(Integer selectorId) {
        this.selectorId = selectorId;
    }

    public ProductStructSelectorInfo getSelectorInfo() {
        return selectorInfo;
    }

    public void setSelectorInfo(ProductStructSelectorInfo selectorInfo) {
        this.selectorInfo = selectorInfo;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return "option";
    }

    public List<ProductStructRelationInfo> getRelationInfos() {
        return relationInfos;
    }

    public void setRelationInfos(List<ProductStructRelationInfo> relationInfos) {
        this.relationInfos = relationInfos;
    }
}
