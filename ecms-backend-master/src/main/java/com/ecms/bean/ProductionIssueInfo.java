package com.ecms.bean;

/**
 * @author Richie
 * @version 2018/9/16 16:47
 */
public class ProductionIssueInfo extends BaseCompanyAndStatusInfo {

    //生产单号
    private String productionNumber;

    //物料id
    private Integer materialId;

    //发料总数
    private Float issueQty;

    //plan表id
    private Integer planId;

    //发料单号
    private String issuanceNumber;

    //已使用数量
    private Float usedQty;

    /**工厂物料编码**/
    private String factoryMaterialCode;

    /**物料类型**/
    private String number;

    /**原图材料**/
    private String originalMaterial;

    /**图号**/
    private String drawingCode;

    /**参数**/
    private String materialBomParamValueStr;

    /**物料来源**/
    private String source;

    /**剩余未使用的发料数量**/
    private Float surplusIssueQty;

    /**订单编号**/
    private String orderCode;

    /**合同编号**/
    private String contractId;

    /**顺序编号**/
    private String sequenceNumber;

    /**生产类型:1-计划单，2-手工添加，3-返工，4-返修，5-报废生产**/
    private Integer productionType;


    public String getProductionNumber() {
        return productionNumber;
    }

    public void setProductionNumber(String productionNumber) {
        this.productionNumber = productionNumber;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public Float getIssueQty() {
        return issueQty;
    }

    public void setIssueQty(Float issueQty) {
        this.issueQty = issueQty;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getIssuanceNumber() {
        return issuanceNumber;
    }

    public void setIssuanceNumber(String issuanceNumber) {
        this.issuanceNumber = issuanceNumber;
    }

    public Float getUsedQty() {
        return usedQty;
    }

    public void setUsedQty(Float usedQty) {
        this.usedQty = usedQty;
    }

    public String getFactoryMaterialCode() {
        return factoryMaterialCode;
    }

    public void setFactoryMaterialCode(String factoryMaterialCode) {
        this.factoryMaterialCode = factoryMaterialCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOriginalMaterial() {
        return originalMaterial;
    }

    public void setOriginalMaterial(String originalMaterial) {
        this.originalMaterial = originalMaterial;
    }

    public String getDrawingCode() {
        return drawingCode;
    }

    public void setDrawingCode(String drawingCode) {
        this.drawingCode = drawingCode;
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

    public Float getSurplusIssueQty() {
        return surplusIssueQty;
    }

    public void setSurplusIssueQty(Float surplusIssueQty) {
        this.surplusIssueQty = surplusIssueQty;
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

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public Integer getProductionType() {
        return productionType;
    }

    public void setProductionType(Integer productionType) {
        this.productionType = productionType;
    }
}
