package com.ecms.bean;

/**
 * @author Richie
 * @version 2019/1/9 13:55
 */
public class ProduceMaterialReturnLogInfo extends BaseCompanyAndStatusInfo{
    /**仓库id**/
    private Integer repertoryId;

    /**生产单号**/
    private String productionNumber;

    /**物料名称**/
    private String materialName;

    /**合同号**/
    private String contractId;

    /**物料id**/
    private Integer materialId;

    /**退料数量**/
    private Float returnQty;

    /**领料单号**/
    private String issuanceNumber;

    /**操作人id**/
    private Integer operatingPersonnelId;

    /**物料批次**/
    private String materialBatch;

    /**退料单号**/
    private String returnNumber;

    /**仓库物料关联id**/
    private Integer materialRepertoryDetailId;

    /**操作人姓名**/
    private String operatingPersonnelName;

    /**物料编码**/
    private String materialCode;

    /**仓库名称**/
    private String repertoryName;

    /**工厂物料编码**/
    private String factoryMaterialCode;

    /**退料时间**/
    private String returnDate;

    /**生产计划表关联id**/
    private Integer planId;

    /**来源**/
    private String source;

    public Integer getRepertoryId() {
        return repertoryId;
    }

    public void setRepertoryId(Integer repertoryId) {
        this.repertoryId = repertoryId;
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

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public Float getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(Float returnQty) {
        this.returnQty = returnQty;
    }

    public String getIssuanceNumber() {
        return issuanceNumber;
    }

    public void setIssuanceNumber(String issuanceNumber) {
        this.issuanceNumber = issuanceNumber;
    }

    public Integer getOperatingPersonnelId() {
        return operatingPersonnelId;
    }

    public void setOperatingPersonnelId(Integer operatingPersonnelId) {
        this.operatingPersonnelId = operatingPersonnelId;
    }

    public String getMaterialBatch() {
        return materialBatch;
    }

    public void setMaterialBatch(String materialBatch) {
        this.materialBatch = materialBatch;
    }

    public String getReturnNumber() {
        return returnNumber;
    }

    public void setReturnNumber(String returnNumber) {
        this.returnNumber = returnNumber;
    }

    public Integer getMaterialRepertoryDetailId() {
        return materialRepertoryDetailId;
    }

    public void setMaterialRepertoryDetailId(Integer materialRepertoryDetailId) {
        this.materialRepertoryDetailId = materialRepertoryDetailId;
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

    public String getRepertoryName() {
        return repertoryName;
    }

    public void setRepertoryName(String repertoryName) {
        this.repertoryName = repertoryName;
    }

    public String getFactoryMaterialCode() {
        return factoryMaterialCode;
    }

    public void setFactoryMaterialCode(String factoryMaterialCode) {
        this.factoryMaterialCode = factoryMaterialCode;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
