package com.ecms.bean;

public class SellerOrderProductInfo extends BaseCompanyAndStatusInfo {

    private Integer orderId;
    private Integer productId;
    private String productCode;
    private String productName;
    private String deliveryDate;
    private Integer taxInclude;
    private Float productNumber;
    private String unit;
    private Integer retailPrice;
    private Integer taxRate;
    private Integer untaxedPrice;
    private Integer taxes;
    private String retailPriceDisplay;
    private String untaxedPriceDisplay;
    private String taxesDisplay;
    private MaterialBomInfo materialBomInfo;
    private String source;
    //库存数
    private Float inventoryQty;

    //已经发货数量
    private Float alreadySendQty;

    //待发数量
    private Float stayDeliveryQty;

    //工厂内部编码
    private String factoryMaterialCode;

    //顺序编码
    private Integer sequenceNumber;

    //物料类型
    private String type;

    //材料
    private String originalMaterial;

    //参数
    private String materialBomParamValueStr;

    //物料状态 0:bom制作 1:bom待下发  2:bom已下发
    private Integer materialStatus;

    //物料状态页面展示字段
    private String materialStatusValue;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Integer getTaxInclude() {
        return taxInclude;
    }

    public void setTaxInclude(Integer taxInclude) {
        this.taxInclude = taxInclude;
    }

    public Float getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(Float productNumber) {
        this.productNumber = productNumber;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Integer retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Integer getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Integer taxRate) {
        this.taxRate = taxRate;
    }

    public Integer getUntaxedPrice() {
        return untaxedPrice;
    }

    public void setUntaxedPrice(Integer untaxedPrice) {
        this.untaxedPrice = untaxedPrice;
    }

    public Integer getTaxes() {
        return taxes;
    }

    public void setTaxes(Integer taxes) {
        this.taxes = taxes;
    }

    public String getRetailPriceDisplay() {
        return retailPriceDisplay;
    }

    public void setRetailPriceDisplay(String retailPriceDisplay) {
        this.retailPriceDisplay = retailPriceDisplay;
    }

    public String getUntaxedPriceDisplay() {
        return untaxedPriceDisplay;
    }

    public void setUntaxedPriceDisplay(String untaxedPriceDisplay) {
        this.untaxedPriceDisplay = untaxedPriceDisplay;
    }

    public String getTaxesDisplay() {
        return taxesDisplay;
    }

    public void setTaxesDisplay(String taxesDisplay) {
        this.taxesDisplay = taxesDisplay;
    }

    public MaterialBomInfo getMaterialBomInfo() {
        return materialBomInfo;
    }

    public void setMaterialBomInfo(MaterialBomInfo materialBomInfo) {
        this.materialBomInfo = materialBomInfo;
    }

    public Float getInventoryQty() {
        return inventoryQty;
    }

    public void setInventoryQty(Float inventoryQty) {
        this.inventoryQty = inventoryQty;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Float getAlreadySendQty() {
        return alreadySendQty;
    }

    public void setAlreadySendQty(Float alreadySendQty) {
        this.alreadySendQty = alreadySendQty;
    }

    public String getFactoryMaterialCode() {
        return factoryMaterialCode;
    }

    public void setFactoryMaterialCode(String factoryMaterialCode) {
        this.factoryMaterialCode = factoryMaterialCode;
    }

    public Float getStayDeliveryQty() {
        return stayDeliveryQty;
    }

    public void setStayDeliveryQty(Float stayDeliveryQty) {
        this.stayDeliveryQty = stayDeliveryQty;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOriginalMaterial() {
        return originalMaterial;
    }

    public void setOriginalMaterial(String originalMaterial) {
        this.originalMaterial = originalMaterial;
    }

    public String getMaterialBomParamValueStr() {
        return materialBomParamValueStr;
    }

    public void setMaterialBomParamValueStr(String materialBomParamValueStr) {
        this.materialBomParamValueStr = materialBomParamValueStr;
    }

    public Integer getMaterialStatus() {
        return materialStatus;
    }

    public void setMaterialStatus(Integer materialStatus) {
        this.materialStatus = materialStatus;
    }

    public String getMaterialStatusValue() {
        return materialStatusValue;
    }

    public void setMaterialStatusValue(String materialStatusValue) {
        this.materialStatusValue = materialStatusValue;
    }
}
