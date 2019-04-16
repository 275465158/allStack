package com.ecms.bean;

/**
 * 出库记录info
 * @author Richie
 * @version 2018/10/29 10:08
 */
public class MaterialRepertoryOutboundInfo extends BaseCompanyAndStatusInfo {
    /**物料code**/
    private String materialCode;
    /**物料id**/
    private Integer materialId;
    /**物料名称**/
    private String materialName;
    /**仓库id**/
    private Integer repertoryId;
    /**出库数量**/
    private Float inventoryQty;
    /**出库时间**/
    private String outboundDate;
    /**出库人id**/
    private Integer outboundPersonId;
    /**领料人id**/
    private Integer takeMaterialId;
    /**领料人用途**/
    private String pickingUseage;
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
