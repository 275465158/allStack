package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 收货entity
 * @author Richie
 * @version 2018/10/25 17:14
 */
@Table(name = "material_repertory_delivery")
public class MaterialRepertoryDeliveryEntity extends BaseCompanyAndStatusEntity {
    /**仓库id**/
    @Column(name="repertory_id")
    private Integer repertoryId;
    /**物料id**/
    @Column(name="material_id")
    private Integer materialId;
    /**数量**/
    @Column(name="inventory_qty")
    private Float inventoryQty;
    /**供应商id**/
    @Column(name="supplier_id")
    private Integer supplierId;
    /**物料批次**/
    @Column(name="material_batch")
    private String materialBatch;
    /**物料编码**/
    @Column(name="material_code")
    private String materialCode;
    /**物料名称**/
    @Column(name="material_name")
    private String materialName;
    /**收货时间**/
    @Column(name="delivery_date")
    private String deliveryDate;
    /**收货人**/
    @Column(name="operating_personnel_id")
    private Integer operatingPersonnelId;
    /**采购原因**/
    @Column(name="purchase_reason")
    private String purchaseReason;
    /**采购单号**/
    @Column(name="purchase_code")
    private String purchaseCode;
    /**采购价**/
    @Column(name="purchase_price")
    private BigDecimal purchasePrice;
    /**收货检测状态**/
    @Column(name="delivery_status")
    private Integer deliveryStatus;
    /**采购单id**/
    @Column(name="purchase_orders_id")
    private Integer purchaseOrdersId;
    /**收货单号**/
    @Column(name="delivery_code")
    private String deliveryCode;
    /**操作人**/
    @Column(name="person")
    private String person;
    /**操作时间**/
    @Column(name="operation_date")
    private String operationDate;
    //列表编号，每个公司都从1开始
    @Column(name="delivery_number")
    private Integer deliveryNumber;
    //采购单明细id
    @Column(name="purchase_orders_list_id")
    private Integer purchaseOrdersListId;
    /**工厂物料编码**/
    @Column(name="factory_material_code")
    private String factoryMaterialCode;

    /**物料类型**/
    @Column(name="number")
    private String number;

    /**原图材料**/
    @Column(name="original_material")
    private String originalMaterial;

    /**图号**/
    @Column(name="drawing_code")
    private String drawingCode;

    /**参数**/
    @Column(name="material_bom_param_value_str")
    private String materialBomParamValueStr;

    /**物料来源**/
    @Column(name="source")
    private String source;

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

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Integer getOperatingPersonnelId() {
        return operatingPersonnelId;
    }

    public void setOperatingPersonnelId(Integer operatingPersonnelId) {
        this.operatingPersonnelId = operatingPersonnelId;
    }
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

    public Integer getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(Integer deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Integer getPurchaseOrdersId() {
        return purchaseOrdersId;
    }

    public void setPurchaseOrdersId(Integer purchaseOrdersId) {
        this.purchaseOrdersId = purchaseOrdersId;
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

    public Integer getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(Integer deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public Integer getPurchaseOrdersListId() {
        return purchaseOrdersListId;
    }

    public void setPurchaseOrdersListId(Integer purchaseOrdersListId) {
        this.purchaseOrdersListId = purchaseOrdersListId;
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
}
