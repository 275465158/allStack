package com.ecms.bean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 仓库物料明细
 * @author Richie
 * @version 2018/10/25 17:07
 */
public class MaterialRepertoryDeliveryInfo extends BaseCompanyAndStatusInfo {
    /**仓库id**/
    private Integer repertoryId;
    /**物料id**/
    private Integer materialId;
    /**数量**/
    private Float inventoryQty;
    /**供应商id**/
    private Integer supplierId;
    /**物料批次**/
    private String materialBatch;
    /**物料编码**/
    private String materialCode;
    /**物料名称**/
    private String materialName;
    /**收货时间**/
    private String deliveryDate;
    /**收货人Id**/
    private Integer operatingPersonnelId;
    /**采购原因**/
    private String purchaseReason;
    /**采购单号**/
    private String purchaseCode;
    /**采购价**/
    private BigDecimal purchasePrice;
    /**采购单id**/
    private Integer purchaseId;
    /**入库状态 0未入库 1已入库 **/
    private Integer deliveryStatus;
    /**原图材料**/
    private String originalMaterial;
    /**参数**/
    private String paramValue;
    /**供应商名称**/
    private String supplierName;
    /**员工id**/
    private Integer employeeId;
    /**采购单id**/
    private Integer purchaseOrdersId;
    //总的收货数量
    private Float oldDeliveryQty;
    //已收货数量
    private Float receivedQty;
    //收货单号
    private String deliveryCode;
    //操作人
    private String person;
    //操作时间
    private String operationDate;
    //采购明细id
    private Integer purchaseOrdersListId;
    //列表编号，每个公司都从1开始
    private Integer deliveryNumber;
    /**工厂物料编码**/
    private String factoryMaterialCode;

    /**物料类型**/
    private String number;

    /**图号**/
    private String drawingCode;

    /**参数**/
    private String materialBomParamValueStr;

    /**物料来源**/
    private String source;

    /**检测数量**/
    private Float completedQty;

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

    public Float getInventoryQty() {
        return inventoryQty;
    }

    public void setInventoryQty(Float inventoryQty) {
        this.inventoryQty = inventoryQty;
    }

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

    public Integer getOperatingPersonnelId() {
        return operatingPersonnelId;
    }

    public void setOperatingPersonnelId(Integer operatingPersonnelId) { this.operatingPersonnelId = operatingPersonnelId; }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getPurchaseReason() {
        return purchaseReason;
    }

    public void setPurchaseReason(String purchaseReason) {
        this.purchaseReason = purchaseReason;
    }

    public String getPurchaseCode() {
        return purchaseCode;
    }

    public void setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
    }

    public String getDeliveryDate() { return deliveryDate; }

    public void setDeliveryDate(String deliveryDate) { this.deliveryDate = deliveryDate; }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Integer getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(Integer deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getOriginalMaterial() {
        return originalMaterial;
    }

    public void setOriginalMaterial(String originalMaterial) {
        this.originalMaterial = originalMaterial;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getPurchaseOrdersId() {
        return purchaseOrdersId;
    }

    public void setPurchaseOrdersId(Integer purchaseOrdersId) {
        this.purchaseOrdersId = purchaseOrdersId;
    }

    public Float getOldDeliveryQty() {
        return oldDeliveryQty;
    }

    public void setOldDeliveryQty(Float oldDeliveryQty) {
        this.oldDeliveryQty = oldDeliveryQty;
    }

    public Float getReceivedQty() {
        return receivedQty;
    }

    public void setReceivedQty(Float receivedQty) {
        this.receivedQty = receivedQty;
    }

    public String getDeliveryCode() {
        return deliveryCode;
    }

    public void setDeliveryCode(String deliveryCode) {
        this.deliveryCode = deliveryCode;
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

    public Integer getPurchaseOrdersListId() {
        return purchaseOrdersListId;
    }

    public void setPurchaseOrdersListId(Integer purchaseOrdersListId) {
        this.purchaseOrdersListId = purchaseOrdersListId;
    }

    public Integer getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(Integer deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
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

    public Float getCompletedQty() {
        return completedQty;
    }

    public void setCompletedQty(Float completedQty) {
        this.completedQty = completedQty;
    }
}
