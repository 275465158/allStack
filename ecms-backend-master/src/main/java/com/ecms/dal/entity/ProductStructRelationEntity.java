package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "product_struct_relation")
public class ProductStructRelationEntity extends BaseCompanyAndStatusEntity {
    @Column(name = "product_struct_relation_option_id")
    private Integer optionId;

    @Column(name = "product_struct_relation_selector_id")
    private Integer selectId;

    @Column(name = "order_by")
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

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }
}
