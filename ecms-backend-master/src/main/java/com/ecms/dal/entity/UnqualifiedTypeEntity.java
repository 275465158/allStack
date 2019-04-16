package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author Richie
 * @version 2018/12/19 16:44
 */
@Table(name = "unqualified_type")
public class UnqualifiedTypeEntity extends BaseCompanyAndStatusEntity {
    @Column(name = "unqualified_type")
    private String unqualifiedType;

    public String getUnqualifiedType() {
        return unqualifiedType;
    }

    public void setUnqualifiedType(String unqualifiedType) {
        this.unqualifiedType = unqualifiedType;
    }
}
