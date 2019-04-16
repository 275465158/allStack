package com.ecms.bean;

import java.util.List;
import java.util.Map;

public class ProductionPlanInfo extends BaseCompanyAndStatusInfo {

    /**生产项次**/
    private String productBatch;
    /**计划生产时间**/
    private String planProductDate;
    /**最晚生产日期**/
    private String latestProductDate;
    /***数量*/
    private Float materialNum;
    /**原图材料**/
    private String originalMaterial;
    /**单位**/
    private String materialUnit;
    /**参数**/
    private String materialParam;
    /**生产单号**/
    private String productionNumber;
    /**生产状态**/
    private Integer productionStatus;
    /**生产完成时间**/
    private String completeDate;
    /**生产类型:1-计划单，2-手工添加，3-返工，4-返修，5-报废生产**/
    private Integer productionType;
    /**生产类型名称**/
    private String productionTypeName;
    /**检测状态**/
    private Integer checkFlag;
    /**产品类型 1：产品，0：物料**/
    private Integer productType;
    /**物料名称**/
    private String materialName;
    /**物料id**/
    private Integer materialId;
    /**合同号**/
    private String contractId;
    /**物料编码**/
    private String materialCode;
    /**订单号**/
    private String orderCode;
    /**物料参数**/
    private String materialParameter;
    /**制单人姓名**/
    private String preparedByName;
    /**制单时间**/
    private String preparedTime;
    /**制单人id**/
    private Integer preparedById;
    /**生产发料list**/
    private List<Map<String,Object>> materialTable;
    /**发料数量**/
    private Float issueQty;
    /**上级生产单号**/
    private String parentProductionNumber;
    /**物料类型**/
    private String materialType;
    /**配额比**/
    private Float quantity;
    /**已完成数量**/
    private Float completedQty;
    /**待生产数量**/
    private Float stayProductionNum;
    /**工厂物料编码**/
    private String factoryMaterialCode;
    /**发料flag1-计划单，2-返工返修**/
    private Integer issueFlag;
    /**物料类型**/
    private String numberType;
    /**图号**/
    private String drawingCode;
    /**顺序编号**/
    private String sequenceNumber;

    /**物料类型**/
    private String number;

    /**参数**/
    private String materialBomParamValueStr;

    /**物料来源**/
    private String source;

    /**商标**/
    private String tradeMark;

    /**待生产数量**/
    private Float stayProductionQty;

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

    public String getMaterialParam() {
        return materialParam;
    }

    public void setMaterialParam(String materialParam) {
        this.materialParam = materialParam;
    }

    public String getProductionNumber() {
        return productionNumber;
    }

    public void setProductionNumber(String productionNumber) {
        this.productionNumber = productionNumber;
    }

    public Integer getProductionStatus() {
        return productionStatus;
    }

    public void setProductionStatus(Integer productionStatus) {
        this.productionStatus = productionStatus;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }

    public Integer getProductionType() {
        return productionType;
    }

    public void setProductionType(Integer productionType) {
        this.productionType = productionType;
    }

    public Integer getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(Integer checkFlag) {
        this.checkFlag = checkFlag;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
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

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialParameter() {
        return materialParameter;
    }

    public void setMaterialParameter(String materialParameter) {
        this.materialParameter = materialParameter;
    }

    public String getPreparedByName() {
        return preparedByName;
    }

    public void setPreparedByName(String preparedByName) {
        this.preparedByName = preparedByName;
    }

    public String getPreparedTime() {
        return preparedTime;
    }

    public void setPreparedTime(String preparedTime) {
        this.preparedTime = preparedTime;
    }

    public Integer getPreparedById() {
        return preparedById;
    }

    public void setPreparedById(Integer preparedById) {
        this.preparedById = preparedById;
    }

    public List<Map<String, Object>> getMaterialTable() {
        return materialTable;
    }

    public void setMaterialTable(List<Map<String, Object>> materialTable) {
        this.materialTable = materialTable;
    }

    public Float getIssueQty() {
        return issueQty;
    }

    public void setIssueQty(Float issueQty) {
        this.issueQty = issueQty;
    }

    public String getParentProductionNumber() {
        return parentProductionNumber;
    }

    public void setParentProductionNumber(String parentProductionNumber) {
        this.parentProductionNumber = parentProductionNumber;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Float getCompletedQty() {
        return completedQty;
    }

    public void setCompletedQty(Float completedQty) {
        this.completedQty = completedQty;
    }

    public Float getStayProductionNum() {
        return stayProductionNum;
    }

    public void setStayProductionNum(Float stayProductionNum) {
        this.stayProductionNum = stayProductionNum;
    }

    public String getFactoryMaterialCode() {
        return factoryMaterialCode;
    }

    public void setFactoryMaterialCode(String factoryMaterialCode) {
        this.factoryMaterialCode = factoryMaterialCode;
    }

    public String getProductionTypeName() {
        return productionTypeName;
    }

    public void setProductionTypeName(String productionTypeName) {
        this.productionTypeName = productionTypeName;
    }

    public Integer getIssueFlag() {
        return issueFlag;
    }

    public void setIssueFlag(Integer issueFlag) {
        this.issueFlag = issueFlag;
    }

    public String getNumberType() {
        return numberType;
    }

    public void setNumberType(String numberType) {
        this.numberType = numberType;
    }

    public String getDrawingCode() {
        return drawingCode;
    }

    public void setDrawingCode(String drawingCode) {
        this.drawingCode = drawingCode;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMaterialBomParamValueStr() {
        return materialBomParamValueStr;
    }

    public void setMaterialBomParamValueStr(String materialBomParamValueStr) {
        this.materialBomParamValueStr = materialBomParamValueStr;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTradeMark() {
        return tradeMark;
    }

    public void setTradeMark(String tradeMark) {
        this.tradeMark = tradeMark;
    }

    public Float getStayProductionQty() {
        return stayProductionQty;
    }

    public void setStayProductionQty(Float stayProductionQty) {
        this.stayProductionQty = stayProductionQty;
    }
}
