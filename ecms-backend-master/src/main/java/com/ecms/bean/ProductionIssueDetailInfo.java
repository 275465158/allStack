package com.ecms.bean;

/**
 * @author Richie
 * @version 2018/9/16 16:47
 */
public class ProductionIssueDetailInfo extends BaseCompanyAndStatusInfo {

    /**生产单号**/
    private String productionNumber;
    /**生产成品名称**/
    private String materialName;
    /**物料编码**/
    private String materialCode;
    /**合同号**/
    private String contractId;
    /**仓库id**/
    private Integer repertoryId;
    /**产品id**/
    private Integer materialId;
    /**发料数量**/
    private Float issueQty;
    /**生产计划表关联id**/
    private Integer planId;
    /**操作人id**/
    private Integer operatingPersonnelId;
    /**仓库名称**/
    private String repertoryName;
    /**操作人姓名**/
    private String operatingPersonnelName;
    /**物料批次**/
    private String materialBatch;
    /**发料单号**/
    private String issuanceNumber;
    /**仓库物料关联id**/
    private Integer materialRepertoryDetailId;
    /**退料数量**/
    private Float returndQty;
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

    /**可退数量**/
    private Float isReturnQty;

    /**使用数量**/
    private Float usedQty;


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

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Integer getRepertoryId() {
        return repertoryId;
    }

    public void setRepertoryId(Integer repertoryId) {
        this.repertoryId = repertoryId;
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

    public Integer getOperatingPersonnelId() {
        return operatingPersonnelId;
    }

    public void setOperatingPersonnelId(Integer operatingPersonnelId) {
        this.operatingPersonnelId = operatingPersonnelId;
    }

    public String getRepertoryName() {
        return repertoryName;
    }

    public void setRepertoryName(String repertoryName) {
        this.repertoryName = repertoryName;
    }

    public String getOperatingPersonnelName() {
        return operatingPersonnelName;
    }

    public void setOperatingPersonnelName(String operatingPersonnelName) {
        this.operatingPersonnelName = operatingPersonnelName;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialBatch() {
        return materialBatch;
    }

    public void setMaterialBatch(String materialBatch) {
        this.materialBatch = materialBatch;
    }

    public String getIssuanceNumber() {
        return issuanceNumber;
    }

    public void setIssuanceNumber(String issuanceNumber) {
        this.issuanceNumber = issuanceNumber;
    }

    public Integer getMaterialRepertoryDetailId() {
        return materialRepertoryDetailId;
    }

    public void setMaterialRepertoryDetailId(Integer materialRepertoryDetailId) {
        this.materialRepertoryDetailId = materialRepertoryDetailId;
    }

    public Float getReturndQty() {
        return returndQty;
    }

    public void setReturndQty(Float returndQty) {
        this.returndQty = returndQty;
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

    public Float getIsReturnQty() {
        return isReturnQty;
    }

    public void setIsReturnQty(Float isReturnQty) {
        this.isReturnQty = isReturnQty;
    }

    public Float getUsedQty() {
        return usedQty;
    }

    public void setUsedQty(Float usedQty) {
        this.usedQty = usedQty;
    }
}
