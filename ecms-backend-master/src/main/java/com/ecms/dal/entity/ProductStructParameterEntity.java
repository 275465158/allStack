package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "product_struct_parameter")
public class ProductStructParameterEntity extends BaseCompanyAndStatusEntity {

    @Column(name = "product_struct_parameter_struct_id")
    private Integer structId;

    @Column(name = "product_struct_parameter_select_id")
    private Integer selectId;

    @Column(name = "product_struct_parameter_order_by")
    private Integer orderBy;

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

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }
}
