package com.ecms.bean;

/**
 * @author Richie
 * @version 2019/1/7 16:33
 */
public class ProductionLogDetailInfo extends BaseCompanyAndStatusInfo {
    //物料id
    private Integer materialId;

    //生产记录表关联id
    private Integer logId;

    //完成数量
    private Float completeQty;

    //生产单号
    private String productionNumber;

    //物料名称
    private String materialName;

    //物料编码
    private String materialCode;

    //生产项次
    private String productBatch;

    //物料名称
    private Float materialNum;

    //检测状态
    private Integer checkStatus;

    //工厂物料编码
    private String factoryMaterialCode;

    //订单编号
    private String orderCode;

    //合同号
    private String contractId;

    //原图材料
    private String originalMaterial;

    //生产时间
    private String produceDate;

    private Integer processId;

    private Integer operatId;

    private String operatName;

    private String startDate;

    private Integer productStatus;

    private String processName;

    private Float qualifiedNum;

    private Integer processCompleteFlag;

    //制程是否已经完成0：未完成,1：已完成
    private Integer checkFlag;

    private String materialBomParamValueStr;

    //完成数量
    private Float completedQty;

    private String materialUnit;

    //编号
    private String sequenceNumber;


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

    public String getProductionNumber() {
        return productionNumber;
    }

    public void setProductionNumber(String productionNumber) {
        this.productionNumber = productionNumber;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
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

    public Float getMaterialNum() {
        return materialNum;
    }

    public void setMaterialNum(Float materialNum) {
        this.materialNum = materialNum;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getFactoryMaterialCode() {
        return factoryMaterialCode;
    }

    public void setFactoryMaterialCode(String factoryMaterialCode) {
        this.factoryMaterialCode = factoryMaterialCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getOriginalMaterial() {
        return originalMaterial;
    }

    public void setOriginalMaterial(String originalMaterial) {
        this.originalMaterial = originalMaterial;
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

    public String getOperatName() {
        return operatName;
    }

    public void setOperatName(String operatName) {
        this.operatName = operatName;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public Float getQualifiedNum() {
        return qualifiedNum;
    }

    public void setQualifiedNum(Float qualifiedNum) {
        this.qualifiedNum = qualifiedNum;
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

    public String getMaterialBomParamValueStr() {
        return materialBomParamValueStr;
    }

    public void setMaterialBomParamValueStr(String materialBomParamValueStr) {
        this.materialBomParamValueStr = materialBomParamValueStr;
    }

    public Float getCompletedQty() {
        return completedQty;
    }

    public void setCompletedQty(Float completedQty) {
        this.completedQty = completedQty;
    }

    public String getMaterialUnit() {
        return materialUnit;
    }

    public void setMaterialUnit(String materialUnit) {
        this.materialUnit = materialUnit;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
}
