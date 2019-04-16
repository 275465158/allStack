package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author Richie
 * @version 2018/11/5 15:16
 */
@Table(name="material_repertory_outboundlog")
public class MaterialRepertoryOutboundlogEntity extends BaseCompanyAndStatusEntity {
    @Column(name="outbound_date")
    private String outboundDate;
    @Column(name="material_id")
    private Integer materialId;
    @Column(name="material_name")
    private String materialName;
    @Column(name="material_batch")
    private String materialBatch;
    @Column(name="material_code")
    private String materialCode;
    @Column(name="storage_location")
    private String storageLocation;
    @Column(name="supplier_id")
    private Integer supplierId;
    @Column(name="repertory_id")
    private Integer repertoryId;
    @Column(name="qty")
    private Float qty;
    @Column(name="take_material_id")
    private Integer takeMaterialId;
    @Column(name="outbound_person_id")
    private Integer outboundPersonId;
    @Column(name="picking_useage")
    private String pickingUseage;
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

    public String getOutboundDate() {
        return outboundDate;
    }

    public void setOutboundDate(String outboundDate) {
        this.outboundDate = outboundDate;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
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

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getRepertoryId() {
        return repertoryId;
    }

    public void setRepertoryId(Integer repertoryId) {
        this.repertoryId = repertoryId;
    }

    public Float getQty() {
        return qty;
    }

    public void setQty(Float qty) {
        this.qty = qty;
    }

    public Integer getTakeMaterialId() {
        return takeMaterialId;
    }

    public void setTakeMaterialId(Integer takeMaterialId) {
        this.takeMaterialId = takeMaterialId;
    }

    public Integer getOutboundPersonId() {
        return outboundPersonId;
    }

    public void setOutboundPersonId(Integer outboundPersonId) {
        this.outboundPersonId = outboundPersonId;
    }

    public String getPickingUseage() {
        return pickingUseage;
    }

    public void setPickingUseage(String pickingUseage) {
        this.pickingUseage = pickingUseage;
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
