package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "production_test")
public class ProductionTestEntity extends BaseCompanyAndStatusEntity {

    private Integer id;
    @Column(name="production_number")
    private String productionNumber;
    @Column(name="production_type")
    private String productionType;
    @Column(name="product_name")
    private String productName;
    @Column(name="production_batch")
    private String productionBatch;
    @Column(name="total_number")
    private Integer totalNumber;
    @Column(name="unqualified_number")
    private Integer unqualifiedNumber;
    @Column(name="company_id")
    private Integer companyId;
    @Column(name="production_date")
    private String productionDate;
    @Column(name="materiel_status")
    private Integer materielStatus;
    @Column(name="latestProductionDate")
    private String latestProductionDate;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductionNumber() {
        return productionNumber;
    }

    public void setProductionNumber(String productionNumber) {
        this.productionNumber = productionNumber;
    }

    public String getProductionType() {
        return productionType;
    }

    public void setProductionType(String productionType) {
        this.productionType = productionType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductionBatch() {
        return productionBatch;
    }

    public void setProductionBatch(String productionBatch) {
        this.productionBatch = productionBatch;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public Integer getUnqualifiedNumber() {
        return unqualifiedNumber;
    }

    public void setUnqualifiedNumber(Integer unqualifiedNumber) {
        this.unqualifiedNumber = unqualifiedNumber;
    }

    @Override
    public Integer getCompanyId() {
        return companyId;
    }

    @Override
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public Integer getMaterielStatus() {
        return materielStatus;
    }

    public void setMaterielStatus(Integer materielStatus) {
        this.materielStatus = materielStatus;
    }

    public String getLatestProductionDate() {
        return latestProductionDate;
    }

    public void setLatestProductionDate(String latestProductionDate) {
        this.latestProductionDate = latestProductionDate;
    }
}
