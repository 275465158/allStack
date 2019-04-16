package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "product_struct_option")
public class ProductStructOptionEntity extends BaseCompanyAndStatusEntity {
    @Column(name = "product_struct_option_selector_id")
    private Integer selectorId;

    @Column(name = "product_struct_option_value")
    private String optionValue;

    @Column(name = "order_by")
    private Integer orderBy;

    public Integer getSelectorId() {
        return selectorId;
    }

    public void setSelectorId(Integer selectorId) {
        this.selectorId = selectorId;
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
}
