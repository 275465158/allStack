package com.ecms.dal.entity;

import javax.persistence.Table;

@Table(name = "product_parameter_name")
public class ProductParameterNameEntity extends BaseCompanyAndStatusEntity {
    private String name;

    private String unit;

    private String owner;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}