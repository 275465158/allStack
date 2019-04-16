package com.ecms.dal.entity;

import javax.persistence.Table;

@Table(name = "seller_pay_rule")
public class SellerPayRuleEntity extends BaseCompanyAndStatusEntity {

    private String name;
    private Integer period;
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
