package com.ecms.bean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 仓库物料明细
 * @author Richie
 * @version 2018/10/25 17:07
 */
public class MaterialRepertoryDetailInfo extends BaseCompanyAndStatusInfo {
    /**仓库id**/
    private Integer repertoryId;

    /**物料id**/
    private Integer materialId;

    /**物料位置**/
    private String storageLocation;

    /**数量**/
    private Float inventoryQty;

    /**供应商id**/
    private Integer supplierId;

    /**物料批次**/
    private String materialBatch;

    /**物料编码**/
    private String materialCode;

    /**物料名称**/
    private String materialName;

    /**原图材料**/
    private String originalMaterial;

    /**入库时间**/
    private String inboundDate;

    /**收货人Id**/
    private Integer operatingPersonnelId;

    /**采购原因**/
    private String purchaseReason;

    /**物料来源**/
    private String materialSource;

    /**参数**/
    private String materialParam;

    /**收货人**/
    private String operatingPersonnelName;

    /**供应商name**/
    private String supplierName;

    /**仓库名称**/
    private String repertoryName;

    /**物料单位**/
    private String materialUnit;

    /**出库列表list**/
    private List<Map<String,Object >> tableDataRow;

    /**出库时间**/
    private String outboundDate;

    /**领料用途**/
    private String pickingUseage;

    /**领料人id**/
    private Integer takeMaterialId;

    /**总数量**/
    private Float materialTotalQty;

    /**入库list**/
    private List<Map<String,Object>> moreNotifyObject;

    /**采购价**/
    private BigDecimal purchasePrice;

    /**入库总数量**/
    private Float totalNum;

    /**待入库id**/
    private Integer purchaseId;

    /**库位list**/
    private List storageLocationList;

    /**安全库存数**/
    private Float inventorySafeQty;

    /**待入库总数**/
    private Float stayInboundQty;

    /**待收货总数**/
    private Float stayDeliveredQty;

    /**待采购总数**/
    private Float stayPurchaseQty;

    /**订单需求数**/
    private Float orderNeedQty;

    /**参数名称**/
    private String paramName;

    /**检测表id**/
    private Integer checkId;

    /**采购单id**/
    private Integer purchaseOrdersId;

    /**来源**/
    private String source;

    /**不合格数量**/
    private Float unqualifiedQty;

    //物料是否合格1-合格，0-不合格
    private Integer unqualifiedFlag;

    //物料类型
    private String numberType;

    /**工厂物料编码**/
    private String factoryMaterialCode;

    /**物料类型**/
    private String number;

    /**图号**/
    private String drawingCode;

    /**参数**/
    private String materialBomParamValueStr;

    /**订单需求数flag**/
    private Integer ordeNeedFlag;

    /**移位数量**/
    private Float moveQty;

    /**库位**/
    private List<Integer> targetPosition;

    /**发料剩余应发数量**/
    private Float surplusNeedQty;

    /**发料数量**/
    private Float productionIssueQty;

    /**明细id**/
    private Integer detailid;

    /**库存status,0入库,1出库,2发料,3发货**/
    private Integer operatStatus;

    private String concatFieldStr;

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public Float getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Float totalNum) {
        this.totalNum = totalNum;
    }

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

    public String getOriginalMaterial() {
        return originalMaterial;
    }

    public void setOriginalMaterial(String originalMaterial) {
        this.originalMaterial = originalMaterial;
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

    public String getMaterialParam() {
        return materialParam;
    }

    public void setMaterialParam(String materialParam) {
        this.materialParam = materialParam;
    }

    public String getOperatingPersonnelName() {
        return operatingPersonnelName;
    }

    public void setOperatingPersonnelName(String operatingPersonnelName) {
        this.operatingPersonnelName = operatingPersonnelName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getRepertoryName() {
        return repertoryName;
    }

    public void setRepertoryName(String repertoryName) {
        this.repertoryName = repertoryName;
    }

    public String getMaterialUnit() {
        return materialUnit;
    }

    public void setMaterialUnit(String materialUnit) {
        this.materialUnit = materialUnit;
    }

    public List<Map<String, Object>> getTableDataRow() {
        return tableDataRow;
    }

    public void setTableDataRow(List<Map<String, Object>> tableDataRow) {
        this.tableDataRow = tableDataRow;
    }

    public String getOutboundDate() {
        return outboundDate;
    }

    public void setOutboundDate(String outboundDate) {
        this.outboundDate = outboundDate;
    }

    public String getPickingUseage() {
        return pickingUseage;
    }

    public void setPickingUseage(String pickingUseage) {
        this.pickingUseage = pickingUseage;
    }

    public Integer getTakeMaterialId() {
        return takeMaterialId;
    }

    public void setTakeMaterialId(Integer takeMaterialId) {
        this.takeMaterialId = takeMaterialId;
    }

    public Float getMaterialTotalQty() {
        return materialTotalQty;
    }

    public void setMaterialTotalQty(Float materialTotalQty) {
        this.materialTotalQty = materialTotalQty;
    }

    public List<Map<String, Object>> getMoreNotifyObject() {
        return moreNotifyObject;
    }

    public void setMoreNotifyObject(List<Map<String, Object>> moreNotifyObject) {
        this.moreNotifyObject = moreNotifyObject;
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

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public List getStorageLocationList() {
        return storageLocationList;
    }

    public void setStorageLocationList(List storageLocationList) {
        this.storageLocationList = storageLocationList;
    }

    public Float getInventorySafeQty() {
        return inventorySafeQty;
    }

    public void setInventorySafeQty(Float inventorySafeQty) {
        this.inventorySafeQty = inventorySafeQty;
    }

    public Float getStayInboundQty() {
        return stayInboundQty;
    }

    public void setStayInboundQty(Float stayInboundQty) {
        this.stayInboundQty = stayInboundQty;
    }

    public Float getStayDeliveredQty() {
        return stayDeliveredQty;
    }

    public void setStayDeliveredQty(Float stayDeliveredQty) {
        this.stayDeliveredQty = stayDeliveredQty;
    }

    public Float getStayPurchaseQty() {
        return stayPurchaseQty;
    }

    public void setStayPurchaseQty(Float stayPurchaseQty) {
        this.stayPurchaseQty = stayPurchaseQty;
    }

    public Float getOrderNeedQty() {
        return orderNeedQty;
    }

    public void setOrderNeedQty(Float orderNeedQty) {
        this.orderNeedQty = orderNeedQty;
    }

    public Integer getCheckId() {
        return checkId;
    }

    public void setCheckId(Integer checkId) {
        this.checkId = checkId;
    }

    public Integer getPurchaseOrdersId() {
        return purchaseOrdersId;
    }

    public void setPurchaseOrdersId(Integer purchaseOrdersId) {
        this.purchaseOrdersId = purchaseOrdersId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public String getNumberType() {
        return numberType;
    }

    public void setNumberType(String numberType) {
        this.numberType = numberType;
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

    public Integer getOrdeNeedFlag() {
        return ordeNeedFlag;
    }

    public void setOrdeNeedFlag(Integer ordeNeedFlag) {
        this.ordeNeedFlag = ordeNeedFlag;
    }

    public Float getMoveQty() {
        return moveQty;
    }

    public void setMoveQty(Float moveQty) {
        this.moveQty = moveQty;
    }

    public List<Integer> getTargetPosition() {
        return targetPosition;
    }

    public void setTargetPosition(List<Integer> targetPosition) {
        this.targetPosition = targetPosition;
    }

    public Float getSurplusNeedQty() {
        return surplusNeedQty;
    }

    public void setSurplusNeedQty(Float surplusNeedQty) {
        this.surplusNeedQty = surplusNeedQty;
    }

    public Float getProductionIssueQty() {
        return productionIssueQty;
    }

    public void setProductionIssueQty(Float productionIssueQty) {
        this.productionIssueQty = productionIssueQty;
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
