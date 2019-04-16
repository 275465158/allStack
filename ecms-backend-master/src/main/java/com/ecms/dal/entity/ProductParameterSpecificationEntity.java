package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "product_parameter_specification")
public class ProductParameterSpecificationEntity extends BaseCompanyAndStatusEntity {
    private String name;

    @Column(name = "parameter_name")
    private String parameterName;

    @Column(name = "parameter_unit")
    private String parameterUnit;

    @Column(name = "parameter_number")
    private Integer parameterNumber;

    private String owner;

    private String maintainer;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterUnit() {
        return parameterUnit;
    }

    public void setParameterUnit(String parameterUnit) {
        this.parameterUnit = parameterUnit;
    }

    public Integer getParameterNumber() {
        return parameterNumber;
    }

    public void setParameterNumber(Integer parameterNumber) {
        this.parameterNumber = parameterNumber;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getMaintainer() {
        return maintainer;
    }

    public void setMaintainer(String maintainer) {
        this.maintainer = maintainer;
    }
}