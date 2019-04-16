package com.ecms.bean;

/**
 * @author Richie
 * @version 2018/11/2 15:27
 */
public class MaterialRepertoryOutboundlogInfo extends BaseCompanyAndStatusInfo {
    private String outboundDate;
    private Integer materialId;
    private String materialName;
    private String materialBatch;
    private String materialCode;
    private String storageLocation;
    private Integer supplierId;
    private Integer repertoryId;
    private Float qty;
    private Integer takeMaterialId;
    private String takeMaterialName;
    private Integer outboundPersonId;
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

    public String getTakeMaterialName() {
        return takeMaterialName;
    }

    public void setTakeMaterialName(String takeMaterialName) {
        this.takeMaterialName = takeMaterialName;
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
