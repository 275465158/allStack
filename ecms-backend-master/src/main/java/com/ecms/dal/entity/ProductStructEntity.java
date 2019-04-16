package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "product_struct")
public class ProductStructEntity extends BaseCompanyAndStatusEntity {

    @Column(name = "product_struct_product_name")
    private String productName;

    @Column(name = "product_struct_product_type_id")
    private Integer productTypeId;

    @Column(name = "product_struct_unit_name")
    private String unitName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Integer productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
