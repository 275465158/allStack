package com.ecms.bean;

public class ProductStructParameterInfo extends BaseCompanyAndStatusInfo {

    private Integer structId;

    private Integer selectId;

    private ProductStructInfo structInfo;

    private ProductStructSelectorInfo selectorInfo;

    public Integer getStructId() {
        return structId;
    }

    public void setStructId(Integer structId) {
        this.structId = structId;
    }

    public Integer getSelectId() {
        return selectId;
    }

    public void setSelectId(Integer selectId) {
        this.selectId = selectId;
    }

    public ProductStructInfo getStructInfo() {
        return structInfo;
    }

    public void setStructInfo(ProductStructInfo structInfo) {
        this.structInfo = structInfo;
    }

    public ProductStructSelectorInfo getSelectorInfo() {
        return selectorInfo;
    }

    public void setSelectorInfo(ProductStructSelectorInfo selectorInfo) {
        this.selectorInfo = selectorInfo;
    }
}
