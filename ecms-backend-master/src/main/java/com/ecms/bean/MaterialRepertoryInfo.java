package com.ecms.bean;

/**
 * 仓库物料bean
 * @author Richie
 * @version 2018/10/25 16:45
 */
public class MaterialRepertoryInfo extends BaseCompanyAndStatusInfo {
    /**物料id**/
    private Integer materialId;
    /**总库存数**/
    private Float inventoryQty;
    /**订单需求数**/
    private Float orderNeedQty;
    /**安全库存数**/
    private Float inventorySafeQty;
    /**待入库总数**/
    private Float stayInboundQty;
    /**待收货总数**/
    private Float stayDeliveredQty;
    /**待采购总数**/
    private Float stayPurchaseQty;
    /**物料编码**/
    private String materialCode;
    /**物料名称**/
    private String materialName;
    /**原图材料**/
    private String originalMaterial;
    /**参数**/
    private String materialBomParamValueStr;
    /**单位**/
    private String materialUnit;
    /**仓库id**/
    private Integer repertoryId;
    /**来源**/
    private String source;
    /**物料类型**/
    private String productType;
    /**不合格数量**/
    private Float unqualifiedQty;
    /**工厂物料编码**/
    private String factoryMaterialCode;
    /**物料类型**/
    private String number;
    /**图号**/
    private String drawingCode;
    /**物料来源**/
    private String materialSource;
    /**待生产数量**/
    private Float stayProductionQty;
    /**待检测数量**/
    private Float stayCheckQty;
    /**投料数**/
    private Float issueQty;
    /**版本号**/
    private String versionVumStr;

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

    public Float getOrderNeedQty() {
        return orderNeedQty;
    }

    public void setOrderNeedQty(Float orderNeedQty) {
        this.orderNeedQty = orderNeedQty;
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

    public Float getStayPurchaseQty() { return stayPurchaseQty; }

    public void setStayPurchaseQty(Float stayPurchaseQty) { this.stayPurchaseQty = stayPurchaseQty; }

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

    public String getMaterialBomParamValueStr() {
        return materialBomParamValueStr;
    }

    public void setMaterialBomParamValueStr(String materialBomParamValueStr) {
        this.materialBomParamValueStr = materialBomParamValueStr;
    }

    public String getMaterialUnit() {
        return materialUnit;
    }

    public void setMaterialUnit(String materialUnit) {
        this.materialUnit = materialUnit;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Float getUnqualifiedQty() {
        return unqualifiedQty;
    }

    public void setUnqualifiedQty(Float unqualifiedQty) {
        this.unqualifiedQty = unqualifiedQty;
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

    public String getMaterialSource() {
        return materialSource;
    }

    public void setMaterialSource(String materialSource) {
        this.materialSource = materialSource;
    }

    public Float getStayProductionQty() {
        return stayProductionQty;
    }

    public void setStayProductionQty(Float stayProductionQty) {
        this.stayProductionQty = stayProductionQty;
    }

    public Float getStayCheckQty() {
        return stayCheckQty;
    }

    public void setStayCheckQty(Float stayCheckQty) {
        this.stayCheckQty = stayCheckQty;
    }

    public Float getIssueQty() {
        return issueQty;
    }

    public void setIssueQty(Float issueQty) {
        this.issueQty = issueQty;
    }

    public String getVersionVumStr() {
        return versionVumStr;
    }

    public void setVersionVumStr(String versionVumStr) {
        this.versionVumStr = versionVumStr;
    }
}
