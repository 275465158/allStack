package com.ecms.bean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author Richie
 * @version 2018/9/13 14:09
 */
public class WarehousingEntryBeanInfo extends BaseCompanyAndStatusInfo {

    //物料id
    private Integer materialId;

    //仓库id
    private Integer repertoryId;

    //库位
    private String shelfPosition;

    //供应商id
    private Integer supplierId;

    //数量
    private Float qty;

    //批次
    private String materielBatch;

    //采购原因
    private String purchaseCause;

    //原图材料
    private String originOfMateriel;

    //入库时间
    private String materielStockFrom;

    //有效期
    private String materielExpiryDate;

    //签收人
    private String materielConsignee;

    //预计到货时间
    private String estimatedDate;

    //采购订单id
    private Integer purchaseOrderId;

    //采购价格
    private BigDecimal purchasePrice;

    //入库状态 0:待入库1:已入库
    private Integer inboundStatus;

    private List<Map<String,Object>> tableDataRow;

    public List<Map<String, Object>> getTableDataRow() {
        return tableDataRow;
    }

    public void setTableDataRow(List<Map<String, Object>> tableDataRow) {
        this.tableDataRow = tableDataRow;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    private List<Map<String,Object>> moreNotifyObject;

    private Float totalNum;

    private Integer materialSearchId;

    public Integer getMaterialSearchId() {
        return materialSearchId;
    }

    public void setMaterialSearchId(Integer materialSearchId) {
        this.materialSearchId = materialSearchId;
    }

    public Float getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Float totalNum) {
        this.totalNum = totalNum;
    }

    public List<Map<String, Object>> getMoreNotifyObject() {
        return moreNotifyObject;
    }

    public void setMoreNotifyObject(List<Map<String, Object>> moreNotifyObject) {
        this.moreNotifyObject = moreNotifyObject;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public Integer getRepertoryId() {
        return repertoryId;
    }

    public void setRepertoryId(Integer repertoryId) {
        this.repertoryId = repertoryId;
    }

    public String getShelfPosition() {
        return shelfPosition;
    }

    public void setShelfPosition(String shelfPosition) {
        this.shelfPosition = shelfPosition;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Float getQty() {
        return qty;
    }

    public void setQty(Float qty) {
        this.qty = qty;
    }

    public String getMaterielBatch() {
        return materielBatch;
    }

    public void setMaterielBatch(String materielBatch) {
        this.materielBatch = materielBatch;
    }

    public String getPurchaseCause() {
        return purchaseCause;
    }

    public void setPurchaseCause(String purchaseCause) {
        this.purchaseCause = purchaseCause;
    }

    public String getOriginOfMateriel() {
        return originOfMateriel;
    }

    public void setOriginOfMateriel(String originOfMateriel) {
        this.originOfMateriel = originOfMateriel;
    }

    public String getMaterielStockFrom() {
        return materielStockFrom;
    }

    public void setMaterielStockFrom(String materielStockFrom) {
        this.materielStockFrom = materielStockFrom;
    }

    public String getMaterielExpiryDate() {
        return materielExpiryDate;
    }

    public void setMaterielExpiryDate(String materielExpiryDate) {
        this.materielExpiryDate = materielExpiryDate;
    }

    public String getMaterielConsignee() {
        return materielConsignee;
    }

    public void setMaterielConsignee(String materielConsignee) {
        this.materielConsignee = materielConsignee;
    }

    public String getEstimatedDate() {
        return estimatedDate;
    }

    public void setEstimatedDate(String estimatedDate) {
        this.estimatedDate = estimatedDate;
    }

    public Integer getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Integer purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public Integer getInboundStatus() {
        return inboundStatus;
    }

    public void setInboundStatus(Integer inboundStatus) {
        this.inboundStatus = inboundStatus;
    }
}
