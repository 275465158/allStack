package com.ecms.bean;

import com.ecms.common.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductParameterSpecificationInfo extends BaseCompanyAndStatusInfo {
    private String name;

    private String parameterName;

    private String parameterUnit;

    private Integer parameterNumber;

    private String owner;

    private String maintainer;

    private Date created;

    private Date updated;

    private String createdDate;

    private String updatedDate;

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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getCreatedDate() {
        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMATTER);
        return format.format(this.created);
    }


    public String getUpdatedDate() {
        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMATTER);
        return format.format(this.updated);
    }

}