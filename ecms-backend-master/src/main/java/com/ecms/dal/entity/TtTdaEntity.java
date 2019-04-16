package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "tt_tda")
public class TtTdaEntity extends BaseCompanyAndStatusEntity {
    @Column(name = "td_name")
    private Integer tdName;

    @Column(name = "td_odd")
    private String tdOdd;

    public Integer getTdName() {
        return tdName;
    }

    public void setTdName(Integer tdName) {
        this.tdName = tdName;
    }

    public String getTdOdd() {
        return tdOdd;
    }

    public void setTdOdd(String tdOdd) {
        this.tdOdd = tdOdd;
    }
}