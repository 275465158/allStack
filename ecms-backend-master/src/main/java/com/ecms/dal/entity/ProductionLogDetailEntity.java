package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author Richie
 * @version 2019/1/7 14:12
 */
@Table(name = "production_log_detail")
public class ProductionLogDetailEntity extends BaseCompanyAndStatusEntity {
    @Column(name = "material_id")
    private Integer materialId;

    @Column(name = "log_id")
    private Integer logId;

    @Column(name = "complete_qty")
    private Float completeQty;

    @Column(name = "check_status")
    private Integer checkStatus;

    @Column(name = "qualified_percent")
    private String qualifiedPercent;

    @Column(name = "qualified_num")
    private Float qualifiedNum;

    @Column(name = "unQualified_num")
    private Float unQualifiedNum;

    @Column(name = "production_number")
    private String productionNumber;

    @Column(name = "material_code")
    private String materialCode;

    @Column(name = "product_batch")
    private String productBatch;

    @Column(name = "produce_date")
    private String produceDate;

    @Column(name = "process_id")
    private Integer processId;

    @Column(name = "operat_id")
    private Integer operatId;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "product_status")
    private Integer productStatus;

    @Column(name = "process_name")
    private String processName;

    @Column(name = "process_complete_flag")
    private Integer processCompleteFlag;

    @Column(name = "check_flag")
    private Integer checkFlag;

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Float getCompleteQty() {
        return completeQty;
    }

    public void setCompleteQty(Float completeQty) {
        this.completeQty = completeQty;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getQualifiedPercent() {
        return qualifiedPercent;
    }

    public void setQualifiedPercent(String qualifiedPercent) {
        this.qualifiedPercent = qualifiedPercent;
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

    public String getProductionNumber() {
        return productionNumber;
    }

    public void setProductionNumber(String productionNumber) {
        this.productionNumber = productionNumber;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getProductBatch() {
        return productBatch;
    }

    public void setProductBatch(String productBatch) {
        this.productBatch = productBatch;
    }

    public String getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(String produceDate) {
        this.produceDate = produceDate;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public Integer getOperatId() {
        return operatId;
    }

    public void setOperatId(Integer operatId) {
        this.operatId = operatId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public Integer getProcessCompleteFlag() {
        return processCompleteFlag;
    }

    public void setProcessCompleteFlag(Integer processCompleteFlag) {
        this.processCompleteFlag = processCompleteFlag;
    }

    public Integer getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(Integer checkFlag) {
        this.checkFlag = checkFlag;
    }
}
