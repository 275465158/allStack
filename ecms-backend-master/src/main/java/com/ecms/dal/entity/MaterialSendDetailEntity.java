package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author Richie
 * @version 2018/11/29 13:36
 */
@Table(name = "material_send_detail")
public class MaterialSendDetailEntity extends BaseCompanyAndStatusEntity {
    @Column(name = "material_id")
    private Integer materialId;

    @Column(name = "send_qty")
    private Float sendQty;

    @Column(name = "already_send_qty")
    private Float alreadySendQty;

    @Column(name = "contract_id")
    private String contractId;

    @Column(name = "material_code")
    private String materialCode;

    @Column(name = "material_name")
    private String materialName;

    @Column(name = "operation_person_id")
    private Integer operationPersonId;

    @Column(name = "send_date")
    private String sendDate;

    @Column(name = "send_id")
    private Integer sendId;

    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "seller_order_product_id")
    private Integer sellerOrderProductId;

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

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public Float getSendQty() {
        return sendQty;
    }

    public void setSendQty(Float sendQty) {
        this.sendQty = sendQty;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
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

    public Integer getOperationPersonId() {
        return operationPersonId;
    }

    public void setOperationPersonId(Integer operationPersonId) {
        this.operationPersonId = operationPersonId;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public Integer getSendId() {
        return sendId;
    }

    public void setSendId(Integer sendId) {
        this.sendId = sendId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getSellerOrderProductId() {
        return sellerOrderProductId;
    }

    public void setSellerOrderProductId(Integer sellerOrderProductId) {
        this.sellerOrderProductId = sellerOrderProductId;
    }

    public Float getAlreadySendQty() {
        return alreadySendQty;
    }

    public void setAlreadySendQty(Float alreadySendQty) {
        this.alreadySendQty = alreadySendQty;
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
