package com.ecms.dal.entity;

import javax.persistence.Table;

@Table(name = "seller_pay_type")
public class SellerPayTypeEntity extends BaseCompanyAndStatusEntity {

    private String name;
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
