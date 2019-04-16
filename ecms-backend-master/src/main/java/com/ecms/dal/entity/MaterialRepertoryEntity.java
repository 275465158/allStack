package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 物料仓库entity
 * @author Richie
 * @version 2018/10/25 16:53
 */
@Table(name = "material_repertory")
public class MaterialRepertoryEntity extends BaseCompanyAndStatusEntity {
    /**物料id**/
    @Column(name="material_id")
    private Integer materialId;
    /**总库存数**/
    @Column(name="inventory_qty")
    private Float inventoryQty;
    /**订单需求数**/
    @Column(name="order_need_qty")
    private Float orderNeedQty;
    /**安全库存数**/
    @Column(name="inventory_safe_qty")
    private Float inventorySafeQty;
    /**待入库总数**/
    @Column(name="stay_inbound_qty")
    private Float stayInboundQty;
    /**待收货总数**/
    @Column(name="stay_delivered_qty")
    private Float stayDeliveredQty;
    /**待采购总数**/
    @Column(name="stay_purchase_qty")
    private Float stayPurchaseQty;
    /**物料编码**/
    @Column(name="material_code")
    private String materialCode;
    /**物料名称**/
    @Column(name="material_name")
    private String materialName;
    /**原图材料**/
    @Column(name="original_material")
    private String originalMaterial;
    /**参数**/
    @Column(name="material_bom_param_value_str")
    private String materialBomParamValueStr;

    //待检测数量
    @Column(name="stay_check_qty")
    private Float stayCheckQty;

    //待生产数量
    @Column(name="stay_production_qty")
    private Float stayProductionQty;

    /**不合格数量**/
    @Column(name = "unqualified_qty")
    private Float unqualifiedQty;

    /**工厂物料编码**/
    @Column(name="factory_material_code")
    private String factoryMaterialCode;

    /**物料类型**/
    @Column(name="number")
    private String number;

    /**图号**/
    @Column(name="drawing_code")
    private String drawingCode;

    /**物料来源**/
    @Column(name="material_source")
    private String materialSource;

    /**生产计划用掉的数量**/
    @Column(name="plan_stay_qty")
    private Float planStayQty;

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

    public Float getStayPurchaseQty() {
        return stayPurchaseQty;
    }

    public void setStayPurchaseQty(Float stayPurchaseQty) {
        this.stayPurchaseQty = stayPurchaseQty;
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

    public String getMaterialBomParamValueStr() {
        return materialBomParamValueStr;
    }

    public void setMaterialBomParamValueStr(String materialBomParamValueStr) {
        this.materialBomParamValueStr = materialBomParamValueStr;
    }

    public Float getStayCheckQty() {
        return stayCheckQty;
    }

    public void setStayCheckQty(Float stayCheckQty) {
        this.stayCheckQty = stayCheckQty;
    }

    public Float getStayProductionQty() {
        return stayProductionQty;
    }

    public void setStayProductionQty(Float stayProductionQty) {
        this.stayProductionQty = stayProductionQty;
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

    public Float getPlanStayQty() {
        return planStayQty;
    }

    public void setPlanStayQty(Float planStayQty) {
        this.planStayQty = planStayQty;
    }
}
