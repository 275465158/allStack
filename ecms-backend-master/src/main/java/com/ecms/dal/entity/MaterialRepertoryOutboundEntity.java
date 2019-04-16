package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 出库记录entity
 * @author Richie
 * @version 2018/10/29 10:13
 */
@Table(name = "material_repertory_outbound")
public class MaterialRepertoryOutboundEntity extends BaseCompanyAndStatusEntity {
    /**物料code**/
    @Column(name="material_code")
    private String materialCode;
    /**物料id**/
    @Column(name="material_id")
    private Integer materialId;
    /**物料名称**/
    @Column(name="material_name")
    private String materialName;
    /**仓库id**/
    @Column(name="repertory_id")
    private Integer repertoryId;
    /**出库数量**/
    @Column(name="inventory_qty")
    private Float inventoryQty;
    /**出库时间**/
    @Column(name="outbound_date")
    private String outboundDate;
    /**出库人id**/
    @Column(name="outbound_person_id")
    private Integer outboundPersonId;
    /**领料人id**/
    @Column(name=" take_material_id")
    private Integer takeMaterialId;
    /**领料用途**/
    @Column(name=" picking_useage")
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

    public Integer getRepertoryId() {
        return repertoryId;
    }

    public void setRepertoryId(Integer repertoryId) {
        this.repertoryId = repertoryId;
    }

    public Float getInventoryQty() {
        return inventoryQty;
    }

    public void setInventoryQty(Float inventoryQty) {
        this.inventoryQty = inventoryQty;
    }

    public String getOutboundDate() {
        return outboundDate;
    }

    public void setOutboundDate(String outboundDate) {
        this.outboundDate = outboundDate;
    }

    public Integer getOutboundPersonId() {
        return outboundPersonId;
    }

    public void setOutboundPersonId(Integer outboundPersonId) {
        this.outboundPersonId = outboundPersonId;
    }

    public Integer getTakeMaterialId() {
        return takeMaterialId;
    }

    public void setTakeMaterialId(Integer takeMaterialId) {
        this.takeMaterialId = takeMaterialId;
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
