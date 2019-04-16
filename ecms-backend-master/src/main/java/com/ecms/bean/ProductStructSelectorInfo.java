package com.ecms.bean;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProductStructSelectorInfo extends BaseCompanyAndStatusInfo {

    //选择器名称
    private String selectorName;

    //选择器显示名称
    private String selectorDisplayName;

    //备注
    private String comment;

    //optionList 页面传值
    private List<Map<String, String>> optionList;

    //relationList 页面传值
    private List<Map<String, String>> selectList;

    //选项内容列表
    private List<ProductStructOptionInfo> options;

    public String getSelectorName() {
        return selectorName;
    }

    public void setSelectorName(String selectorName) {
        this.selectorName = selectorName;
    }

    public String getSelectorDisplayName() {
        return selectorDisplayName;
    }

    public void setSelectorDisplayName(String selectorDisplayName) {
        this.selectorDisplayName = selectorDisplayName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Map<String, String>> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<Map<String, String>> optionList) {
        this.optionList = optionList;
    }

    public List<Map<String, String>> getSelectList() {
        return selectList;
    }

    public void setSelectList(List<Map<String, String>> selectList) {
        this.selectList = selectList;
    }

    public List<ProductStructOptionInfo> getOptions() {
        return options;
    }

    public void setOptions(List<ProductStructOptionInfo> options) {
        this.options = options;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductStructSelectorInfo that = (ProductStructSelectorInfo) o;
        return Objects.equals(selectorName, that.selectorName) &&
                Objects.equals(selectorDisplayName, that.selectorDisplayName) &&
                Objects.equals(comment, that.comment) &&
                Objects.equals(optionList, that.optionList) &&
                Objects.equals(options, that.options);
    }

    @Override
    public int hashCode() {
        return Objects.hash(selectorName, selectorDisplayName, comment, optionList, options);
    }
}
