package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author Richie
 * @version 2018/11/8 13:44
 */
@Table(name="material_check_detail")
public class MaterialCheckDetailEntity extends BaseCompanyAndStatusEntity {
    @Column(name="repertory_id")
    private Integer repertoryId;
    @Column(name="material_id")
    private Integer materialId;
    @Column(name="storage_location")
    private String storageLocation;
    @Column(name="inventory_qty")
    private Float inventoryQty;
    @Column(name="supplier_id")
    private Integer supplierId;
    @Column(name="material_batch")
    private String materialBatch;
    @Column(name="material_code")
    private String materialCode;
    @Column(name="material_name")
    private String materialName;
    @Column(name = "mrd_id")
    private Integer mrdId;

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

    public Integer getMrdId() {
        return mrdId;
    }

    public void setMrdId(Integer mrdId) {
        this.mrdId = mrdId;
    }
}
