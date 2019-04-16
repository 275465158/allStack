package com.ecms.bean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 仓库物料明细
 * @author Richie
 * @version 2018/10/25 17:07
 */
public class MaterialRepertoryIncomingCheckInfo extends BaseCompanyAndStatusInfo {
    /**仓库id**/
    private Integer repertoryId;
    /**物料id**/
    private Integer materialId;
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
    /**收货时间**/
    private String deliveryDate;
    /**收货人Id**/
    private Integer operatingPersonnelId;
    /**采购原因**/
    private String purchaseReason;
    /**采购单号**/
    private String purchaseCode;
    /**采购价**/
    private BigDecimal purchasePrice;
    /**采购单id**/
    private Integer purchaseId;
    /**入库状态 0未入库 1已入库 **/
    private Integer deliveryStatus;
    /**原图材料**/
    private String originalMaterial;
    /**参数**/
    private String paramValue;
    /**供应商名称**/
    private String supplierName;
    /**员工id**/
    private Integer employeeId;
    /**不合格数量**/
    private Float unqualifiedQty;
    /**合格数量**/
    private Float qualifiedQty;
    /**入库状态：2：待入库，3：已入库**/
    private Integer inboundStutas;
    /**检测时间**/
    private String checkDate;
    /**检测人姓名**/
    private String checkPersonName;
    /**采购单id**/
    private Integer purchaseOrdersId;
    /**收货表id**/
    private Integer deliveryId;
    /**物料来源 1：来料，2：生产**/
    private Integer materialSource;
    //检测单号
    private String checkNumber;
    //合格率
    private String qualifiedPercent;
    //检测比例
    private String checkPercent;
    //检测数量
    private Float checkQty;
    //不合格类型
    private String unqualifiedType;
    //不合格描述
    private String unqualifiedRemark;
    //附件
    private String fileUrl;
    //检测记录list
    private List<Map<String,String>> resultList;
    //不合格类型
    private List<String> unqualifiedTypeFormList;
    //收货单号
    private String deliveryCode;
    //页面展示不合格list
    private List<UnqualifiedTypeListInfo> utliList;
    //页面传过来的新物料id
    private Integer newMaterialId;
    //物料是否合格1-合格，0-不合格
    private Integer unqualifiedFlag;
    //列表编号
    private Integer incomingCheckNumber;
    //检测记录list
    private List<String> temp;
    /**工厂物料编码**/
    private String factoryMaterialCode;

    /**物料类型**/
    private String number;

    /**图号**/
    private String drawingCode;

    /**参数**/
    private String materialBomParamValueStr;

    /**物料来源**/
    private String source;

    //采购明细id
    private Integer purchaseOrdersListId;

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

    public Integer getOperatingPersonnelId() {
        return operatingPersonnelId;
    }

    public void setOperatingPersonnelId(Integer operatingPersonnelId) { this.operatingPersonnelId = operatingPersonnelId; }

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

    public String getPurchaseCode() {
        return purchaseCode;
    }

    public void setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
    }

    public String getDeliveryDate() { return deliveryDate; }

    public void setDeliveryDate(String deliveryDate) { this.deliveryDate = deliveryDate; }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Integer getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(Integer deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getOriginalMaterial() {
        return originalMaterial;
    }

    public void setOriginalMaterial(String originalMaterial) {
        this.originalMaterial = originalMaterial;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Float getUnqualifiedQty() {
        return unqualifiedQty;
    }

    public void setUnqualifiedQty(Float unqualifiedQty) {
        this.unqualifiedQty = unqualifiedQty;
    }

    public Float getQualifiedQty() {
        return qualifiedQty;
    }

    public void setQualifiedQty(Float qualifiedQty) {
        this.qualifiedQty = qualifiedQty;
    }

    public Integer getInboundStutas() {
        return inboundStutas;
    }

    public void setInboundStutas(Integer inboundStutas) {
        this.inboundStutas = inboundStutas;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getCheckPersonName() {
        return checkPersonName;
    }

    public void setCheckPersonName(String checkPersonName) {
        this.checkPersonName = checkPersonName;
    }

    public Integer getPurchaseOrdersId() {
        return purchaseOrdersId;
    }

    public void setPurchaseOrdersId(Integer purchaseOrdersId) {
        this.purchaseOrdersId = purchaseOrdersId;
    }

    public Integer getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Integer deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Integer getMaterialSource() {
        return materialSource;
    }

    public void setMaterialSource(Integer materialSource) {
        this.materialSource = materialSource;
    }


    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getQualifiedPercent() {
        return qualifiedPercent;
    }

    public void setQualifiedPercent(String qualifiedPercent) {
        this.qualifiedPercent = qualifiedPercent;
    }

    public String getCheckPercent() {
        return checkPercent;
    }

    public void setCheckPercent(String checkPercent) {
        this.checkPercent = checkPercent;
    }

    public Float getCheckQty() {
        return checkQty;
    }

    public void setCheckQty(Float checkQty) {
        this.checkQty = checkQty;
    }

    public String getUnqualifiedType() {
        return unqualifiedType;
    }

    public void setUnqualifiedType(String unqualifiedType) {
        this.unqualifiedType = unqualifiedType;
    }

    public String getUnqualifiedRemark() {
        return unqualifiedRemark;
    }

    public void setUnqualifiedRemark(String unqualifiedRemark) {
        this.unqualifiedRemark = unqualifiedRemark;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public List<Map<String, String>> getResultList() {
        return resultList;
    }

    public void setResultList(List<Map<String, String>> resultList) {
        this.resultList = resultList;
    }

    public List<String> getUnqualifiedTypeFormList() {
        return unqualifiedTypeFormList;
    }

    public void setUnqualifiedTypeFormList(List<String> unqualifiedTypeFormList) {
        this.unqualifiedTypeFormList = unqualifiedTypeFormList;
    }

    public String getDeliveryCode() {
        return deliveryCode;
    }

    public void setDeliveryCode(String deliveryCode) {
        this.deliveryCode = deliveryCode;
    }

    public List<UnqualifiedTypeListInfo> getUtliList() {
        return utliList;
    }

    public void setUtliList(List<UnqualifiedTypeListInfo> utliList) {
        this.utliList = utliList;
    }

    public Integer getNewMaterialId() {
        return newMaterialId;
    }

    public void setNewMaterialId(Integer newMaterialId) {
        this.newMaterialId = newMaterialId;
    }

    public Integer getUnqualifiedFlag() {
        return unqualifiedFlag;
    }

    public void setUnqualifiedFlag(Integer unqualifiedFlag) {
        this.unqualifiedFlag = unqualifiedFlag;
    }

    public Integer getIncomingCheckNumber() {
        return incomingCheckNumber;
    }

    public void setIncomingCheckNumber(Integer incomingCheckNumber) {
        this.incomingCheckNumber = incomingCheckNumber;
    }

    public List<String> getTemp() {
        return temp;
    }

    public void setTemp(List<String> temp) {
        this.temp = temp;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getPurchaseOrdersListId() {
        return purchaseOrdersListId;
    }

    public void setPurchaseOrdersListId(Integer purchaseOrdersListId) {
        this.purchaseOrdersListId = purchaseOrdersListId;
    }
}
