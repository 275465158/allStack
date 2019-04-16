package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "production_log")
public class ProductionLogEntity extends BaseCompanyAndStatusEntity {

    @Column(name="production_number")
    private String productionNumber;

    @Column(name="production_type")
    private String productionType;

    @Column(name="material_name")
    private String materialName;

    @Column(name="product_batch")
    private String productBatch;

    @Column(name="plan_product_date")
    private String planProductDate;

    @Column(name="product_time")
    private String productTime;

    @Column(name="latest_product_date")
    private String latestProductDate;

    @Column(name="material_num")
    private Float materialNum;

    @Column(name="qualified_num")
    private Float qualifiedNum;

    @Column(name="unQualified_num")
    private Float unQualifiedNum;

    @Column(name = "material_id")
    private Integer materialId;

    @Column(name = "original_material")
    private String originalMaterial;

    @Column(name = "material_unit")
    private String materialUnit;

    /**检测状态0：待检测，1：已检测**/
    @Column(name = "check_status")
    private Integer checkStatus;

    /**物料编码**/
    @Column(name = "material_code")
    private String materialCode;

    /**合格率**/
    @Column(name = "qualified_percent")
    private String qualifiedPercent;

    /**生产计划表id**/
    @Column(name = "production_plan_id")
    private Integer productionPlanId;

    /**已完成数量**/
    @Column(name = "completed_qty")
    private Float completedQty;

    //附件
    @Column(name="file_url")
    private String fileUrl;

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

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getProductBatch() {
        return productBatch;
    }

    public void setProductBatch(String productBatch) {
        this.productBatch = productBatch;
    }

    public String getPlanProductDate() {
        return planProductDate;
    }

    public void setPlanProductDate(String planProductDate) {
        this.planProductDate = planProductDate;
    }

    public String getProductTime() {
        return productTime;
    }

    public void setProductTime(String productTime) {
        this.productTime = productTime;
    }

    public String getLatestProductDate() {
        return latestProductDate;
    }

    public void setLatestProductDate(String latestProductDate) {
        this.latestProductDate = latestProductDate;
    }

    public Float getMaterialNum() {
        return materialNum;
    }

    public void setMaterialNum(Float materialNum) {
        this.materialNum = materialNum;
    }

    public Float getQualifiedNum() {
        return qualifiedNum;
    }

    public void setQualifiedNum(Float qualifiedNum) {
        this.qualifiedNum = qualifiedNum;
    }

    public Float getUnQualifiedNum() {
        return unQualifiedNum;
    }

    public void setUnQualifiedNum(Float unQualifiedNum) {
        this.unQualifiedNum = unQualifiedNum;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public String getOriginalMaterial() {
        return originalMaterial;
    }

    public void setOriginalMaterial(String originalMaterial) {
        this.originalMaterial = originalMaterial;
    }

    public String getMaterialUnit() {
        return materialUnit;
    }

    public void setMaterialUnit(String materialUnit) {
        this.materialUnit = materialUnit;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getQualifiedPercent() {
        return qualifiedPercent;
    }

    public void setQualifiedPercent(String qualifiedPercent) {
        this.qualifiedPercent = qualifiedPercent;
    }

    public Integer getProductionPlanId() {
        return productionPlanId;
    }

    public void setProductionPlanId(Integer productionPlanId) {
        this.productionPlanId = productionPlanId;
    }

    public Float getCompletedQty() {
        return completedQty;
    }

    public void setCompletedQty(Float completedQty) {
        this.completedQty = completedQty;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
