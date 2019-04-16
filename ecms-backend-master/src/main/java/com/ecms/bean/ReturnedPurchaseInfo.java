package com.ecms.bean;

/**
 * @author Richie
 * @version 2018/12/25 13:40
 */
public class ReturnedPurchaseInfo extends BaseCompanyAndStatusInfo {
    //供应商id
    private Integer supplierId;
    //物料批次
    private String materialBatch;
    //物料编码
    private String materialCode;
    //物料名称
    private String materialName;
    //采购单id
    private Integer purchaseOrdersId;
    //采购单明细id
    private Integer purchaseOrdersListId;
    //采购编号
    private String purchaseCode;
    //退货数量
    private Float returnedPurchaseQty;
    //退货原因
    private String returnedPurchaseReason;
    //仓库id
    private Integer repertoryId;
    //物料类型
    private String numberType;
    //材料
    private String originalMaterial;
    //参数
    private String paramName;
    //物料id
    private Integer materialId;
    //退货单号
    private String returnedCode;
    //操作人
    private String person;
    //操作时间
    private String operationDate;

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getMaterialBatch() {
        return materialBatch;
    }

    public void setMaterialBatch(String materialBatch) {
        this.materialBatch = materialBatch;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public Integer getPurchaseOrdersId() {
        return purchaseOrdersId;
    }

    public void setPurchaseOrdersId(Integer purchaseOrdersId) {
        this.purchaseOrdersId = purchaseOrdersId;
    }

    public Integer getPurchaseOrdersListId() {
        return purchaseOrdersListId;
    }

    public void setPurchaseOrdersListId(Integer purchaseOrdersListId) {
        this.purchaseOrdersListId = purchaseOrdersListId;
    }

    public String getPurchaseCode() {
        return purchaseCode;
    }

    public void setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
    }

    public Float getReturnedPurchaseQty() {
        return returnedPurchaseQty;
    }

    public void setReturnedPurchaseQty(Float returnedPurchaseQty) {
        this.returnedPurchaseQty = returnedPurchaseQty;
    }

    public String getReturnedPurchaseReason() {
        return returnedPurchaseReason;
    }

    public void setReturnedPurchaseReason(String returnedPurchaseReason) {
        this.returnedPurchaseReason = returnedPurchaseReason;
    }

    public Integer getRepertoryId() {
        return repertoryId;
    }

    public void setRepertoryId(Integer repertoryId) {
        this.repertoryId = repertoryId;
    }

    public String getNumberType() {
        return numberType;
    }

    public void setNumberType(String numberType) {
        this.numberType = numberType;
    }

    public String getOriginalMaterial() {
        return originalMaterial;
    }

    public void setOriginalMaterial(String originalMaterial) {
        this.originalMaterial = originalMaterial;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public String getReturnedCode() {
        return returnedCode;
    }

    public void setReturnedCode(String returnedCode) {
        this.returnedCode = returnedCode;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(String operationDate) {
        this.operationDate = operationDate;
    }
}
