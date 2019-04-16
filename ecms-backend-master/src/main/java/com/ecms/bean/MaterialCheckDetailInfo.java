package com.ecms.bean;

import java.util.List;
import java.util.Map;

/**
 * @author Richie
 * @version 2018/11/8 13:41
 */
public class MaterialCheckDetailInfo extends BaseCompanyAndStatusInfo {
    private Integer repertoryId;
    private Integer materialId;
    private String storageLocation;
    private Float inventoryQty;
    private Integer supplierId;
    private String materialBatch;
    private String materialCode;
    private String materialName;
    private Integer mrdId;
    private List<Map<String,Object>> tableDataRow;

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

    public List<Map<String, Object>> getTableDataRow() {
        return tableDataRow;
    }

    public void setTableDataRow(List<Map<String, Object>> tableDataRow) {
        this.tableDataRow = tableDataRow;
    }

    public Integer getMrdId() {
        return mrdId;
    }

    public void setMrdId(Integer mrdId) {
        this.mrdId = mrdId;
    }
}
