package com.ecms.bean;

public class ProductStructRelationInfo extends BaseCompanyAndStatusInfo {

    private Integer optionId;

    private Integer selectId;
    //下级选择器
    private ProductStructSelectorInfo selectorInfo;
    //联动选项
    private ProductStructOptionInfo optionInfo;
    //排序
    private Integer orderBy;

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public Integer getSelectId() {
        return selectId;
    }

    public void setSelectId(Integer selectId) {
        this.selectId = selectId;
    }

    public ProductStructSelectorInfo getSelectorInfo() {
        return selectorInfo;
    }

    public void setSelectorInfo(ProductStructSelectorInfo selectorInfo) {
        this.selectorInfo = selectorInfo;
    }

    public ProductStructOptionInfo getOptionInfo() {
        return optionInfo;
    }

    public void setOptionInfo(ProductStructOptionInfo optionInfo) {
        this.optionInfo = optionInfo;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }
}
