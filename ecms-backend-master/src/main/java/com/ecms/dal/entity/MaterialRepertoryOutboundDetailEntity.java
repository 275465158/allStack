package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author Richie
 * @version 2018/10/29 15:58
 */
@Table(name = "material_repertory_outbound_detail")
public class MaterialRepertoryOutboundDetailEntity extends BaseCompanyAndStatusEntity {
    /**出库表关联id**/
    @Column(name="outbound_id")
    private Integer outboundId;
    /**物料编码**/
    @Column(name="material_code")
    private String materialCode;
    /**物料id**/
    @Column(name="material_id")
    private Integer materialId;
    /**物料名称**/
    @Column(name="material_name")
    private String materialName;
    /**位置**/
    @Column(name="storage_location")
    private String storageLocation;
    /**物料批次**/
    @Column(name="material_batch")
    private String materialBatch;
    /**出库数量**/
    @Column(name="outbound_qty")
    private Float outboundQty;
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

    public Integer getOutboundId() {
        return outboundId;
    }

    public void setOutboundId(Integer outboundId) {
        this.outboundId = outboundId;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
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

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    public String getMaterialBatch() {
        return materialBatch;
    }

    public void setMaterialBatch(String materialBatch) {
        this.materialBatch = materialBatch;
    }

    public Float getOutboundQty() {
        return outboundQty;
    }

    public void setOutboundQty(Float outboundQty) {
        this.outboundQty = outboundQty;
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
