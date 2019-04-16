package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "product_struct_selector")
public class ProductStructSelectorEntity extends BaseCompanyAndStatusEntity{
    @Column(name = "product_struct_selector_name")
    private String selectorName;

    @Column(name = "product_struct_selector_display_name")
    private String selectorDisplayName;

    @Column(name = "product_struct_selector_comment")
    private String comment;

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
}
