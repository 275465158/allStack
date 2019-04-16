package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author Richie
 * @version 2018/11/12 15:16
 */
@Table(name = "production_check_log")
public class ProductionCheckLogEntity extends BaseCompanyAndStatusEntity {
    @Column(name="production_number")
    private String productionNumber;

    @Column(name="material_num")
    private Float materialNum;

    @Column(name="qualified_num")
    private Float qualifiedNum;

    @Column(name="unQualified_num")
    private Float unQualifiedNum;

    @Column(name="qualified_percent")
    private String qualifiedPercent;

    @Column(name="production_log_id")
    private Integer productionLogId;

    @Column(name="material_name")
    private String materialName;

    @Column(name="material_id")
    private Integer materialId;

    @Column(name="material_code")
    private String materialCode;

    @Column(name="check_date")
    private String checkDate;

    @Column(name="check_person_id")
    private Integer checkPersonId;

    @Column(name="production_batch")
    private String productionBatch;

    @Column(name = "check_number")
    private String checkNumber;

    @Column(name = "check_percent")
    private String checkPercent;

    @Column(name = "check_num")
    private Float checkNum;

    @Column(name = "check_person_name")
    private String checkPersonName;

    @Column(name = "unQualified_type")
    private String unQualifiedType;

    @Column(name = "unQualified_remark")
    private String unQualifiedRemark;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "check_result")
    private String checkResult;

    @Column(name = "check_qualified_percent")
    private String checkQualifiedPercent;

    @Column(name="product_log_number")
    private Integer productLogNumber;

    @Column(name="process_id")
    private Integer processId;

    @Column(name="product_person_name")
    private String productPersonName;

    @Column(name="product_person_id")
    private Integer productPersonId;

    @Column(name="product_date")
    private String productDate;

    public String getProductionNumber() {
        return productionNumber;
    }

    public void setProductionNumber(String productionNumber) {
        this.productionNumber = productionNumber;
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

    public String getQualifiedPercent() {
        return qualifiedPercent;
    }

    public void setQualifiedPercent(String qualifiedPercent) {
        this.qualifiedPercent = qualifiedPercent;
    }

    public Integer getProductionLogId() {
        return productionLogId;
    }

    public void setProductionLogId(Integer productionLogId) {
        this.productionLogId = productionLogId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public Integer getCheckPersonId() {
        return checkPersonId;
    }

    public void setCheckPersonId(Integer checkPersonId) {
        this.checkPersonId = checkPersonId;
    }

    public String getProductionBatch() {
        return productionBatch;
    }

    public void setProductionBatch(String productionBatch) {
        this.productionBatch = productionBatch;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getCheckPercent() {
        return checkPercent;
    }

    public void setCheckPercent(String checkPercent) {
        this.checkPercent = checkPercent;
    }

    public Float getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(Float checkNum) {
        this.checkNum = checkNum;
    }

    public String getCheckPersonName() {
        return checkPersonName;
    }

    public void setCheckPersonName(String checkPersonName) {
        this.checkPersonName = checkPersonName;
    }

    public String getUnQualifiedType() {
        return unQualifiedType;
    }

    public void setUnQualifiedType(String unQualifiedType) {
        this.unQualifiedType = unQualifiedType;
    }

    public String getUnQualifiedRemark() {
        return unQualifiedRemark;
    }

    public void setUnQualifiedRemark(String unQualifiedRemark) {
        this.unQualifiedRemark = unQualifiedRemark;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public String getCheckQualifiedPercent() {
        return checkQualifiedPercent;
    }

    public void setCheckQualifiedPercent(String checkQualifiedPercent) {
        this.checkQualifiedPercent = checkQualifiedPercent;
    }

    public Integer getProductLogNumber() {
        return productLogNumber;
    }

    public void setProductLogNumber(Integer productLogNumber) {
        this.productLogNumber = productLogNumber;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public String getProductPersonName() {
        return productPersonName;
    }

    public void setProductPersonName(String productPersonName) {
        this.productPersonName = productPersonName;
    }

    public Integer getProductPersonId() {
        return productPersonId;
    }

    public void setProductPersonId(Integer productPersonId) {
        this.productPersonId = productPersonId;
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }
}
