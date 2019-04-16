package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author Richie
 * @version 2018/12/25 13:47
 */
@Table(name = "returned_purchase")
public class ReturnedPurchaseEntity extends BaseCompanyAndStatusEntity {
    //供应商id
    @Column(name = "supplier_id")
    private Integer supplierId;
    //物料批次
    @Column(name = "material_batch")
    private String materialBatch;
    //物料编码
    @Column(name = "material_code")
    private String materialCode;
    //物料名称
    @Column(name = "material_name")
    private String materialName;
    //采购单id
    @Column(name = "purchase_orders_id")
    private Integer purchaseOrdersId;
    //采购单明细id
    @Column(name="purchase_orders_list_id")
    private Integer purchaseOrdersListId;
    //采购编号
    @Column(name = "purchase_code")
    private String purchaseCode;
    //退货数量
    @Column(name = "returned_purchase_qty")
    private Float returnedPurchaseQty;
    //退货原因
    @Column(name = "returned_rurchase_reason")
    private String returnedPurchaseReason;
    //仓库id
    @Column(name = "repertory_id")
    private Integer repertoryId;
    //物料id
    @Column(name = "material_id")
    private Integer materialId;
    //退货单号
    @Column(name = "returned_code")
    private String returnedCode;
    //操作人
    @Column(name="person")
    private String person;
    //操作时间
    @Column(name="operation_date")
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

    public Integer getPurchaseOrdersListId() {
        return purchaseOrdersListId;
    }

    public void setPurchaseOrdersListId(Integer purchaseOrdersListId) {
        this.purchaseOrdersListId = purchaseOrdersListId;
    }
}
