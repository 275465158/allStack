package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 仓库物料明细entity
 * @author Richie
 * @version 2018/10/25 17:14
 */
@Table(name = "material_repertory_detail")
public class MaterialRepertoryDetailEntity extends BaseCompanyAndStatusEntity {

    /**仓库id**/
    @Column(name="repertory_id")
    private Integer repertoryId;

    /**物料id**/
    @Column(name="material_id")
    private Integer materialId;

    /**物料位置**/
    @Column(name="storage_location")
    private String storageLocation;

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

    /**入库时间**/
    @Column(name="inbound_date")
    private String inboundDate;

    /**收货人**/
    @Column(name="operating_personnel_id")
    private Integer operatingPersonnelId;

    /**采购原因**/
    @Column(name="purchase_reason")
    private String purchaseReason;

    /**物料来源**/
    @Column(name="material_source")
    private String materialSource;

    /**采购价**/
    @Column(name="purchase_price")
    private BigDecimal purchasePrice;

    /**不合格数量**/
    @Column(name = "unqualified_qty")
    private Float unqualifiedQty;

    //物料是否合格1-合格，0-不合格
    @Column(name = "unqualified_flag")
    private Integer unqualifiedFlag;

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

    /**明细id**/
    @Column(name="detail_id")
    private Integer detailid;

    /**库存status,0入库,1出库,2发料,3发货**/
    @Column(name="operat_status")
    private Integer operatStatus;

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

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
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

    public String getInboundDate() {
        return inboundDate;
    }

    public void setInboundDate(String inboundDate) {
        this.inboundDate = inboundDate;
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

    public String getMaterialSource() {
        return materialSource;
    }

    public void setMaterialSource(String materialSource) {
        this.materialSource = materialSource;
    }

    public Float getUnqualifiedQty() {
        return unqualifiedQty;
    }

    public void setUnqualifiedQty(Float unqualifiedQty) {
        this.unqualifiedQty = unqualifiedQty;
    }

    public Integer getUnqualifiedFlag() {
        return unqualifiedFlag;
    }

    public void setUnqualifiedFlag(Integer unqualifiedFlag) {
        this.unqualifiedFlag = unqualifiedFlag;
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

    public Integer getDetailid() {
        return detailid;
    }

    public void setDetailid(Integer detailid) {
        this.detailid = detailid;
    }

    public Integer getOperatStatus() {
        return operatStatus;
    }

    public void setOperatStatus(Integer operatStatus) {
        this.operatStatus = operatStatus;
    }
}
