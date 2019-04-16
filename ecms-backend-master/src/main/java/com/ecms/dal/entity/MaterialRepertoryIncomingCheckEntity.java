package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 仓库物料明细entity
 * @author Richie
 * @version 2018/10/25 17:14
 */
@Table(name = "material_repertory_incoming_check")
public class MaterialRepertoryIncomingCheckEntity extends BaseCompanyAndStatusEntity {
    /**仓库id**/
    @Column(name="repertory_id")
    private Integer repertoryId;
    /**物料id**/
    @Column(name="material_id")
    private Integer materialId;
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
    /**收货时间**/
    @Column(name="delivery_date")
    private String deliveryDate;
    /**收货人**/
    @Column(name="operating_personnel_id")
    private Integer operatingPersonnelId;
    /**采购原因**/
    @Column(name="purchase_reason")
    private String purchaseReason;
    /**采购单号**/
    @Column(name="purchase_code")
    private String purchaseCode;
    /**采购价**/
    @Column(name="purchase_price")
    private BigDecimal purchasePrice;
    /**不合格数量**/
    @Column(name="unqualified_qty")
    private Float unqualifiedQty;
    /**合格数量**/
    @Column(name="qualified_qty")
    private Float qualifiedQty;
    /**入库状态：2：待入库，3：已入库**/
    @Column(name = "inbound_stutas")
    private Integer inboundStutas;
    /**检测时间**/
    @Column(name = "check_date")
    private String checkDate;
    /**采购单id**/
    @Column(name = "purchase_orders_id")
    private Integer purchaseOrdersId;
    /**物料来源 1：来料，2生产**/
    @Column(name = "material_source")
    private Integer materialSource;
    //检测单号
    @Column(name="check_number")
    private String checkNumber;
    //合格率
    @Column(name="qualified_percent")
    private String qualifiedPercent;
    //检测比例
    @Column(name="check_percent")
    private String checkPercent;
    //检测数量
    @Column(name="check_qty")
    private Float checkQty;
    //不合格类型
    @Column(name="unqualified_type")
    private String unqualifiedType;
    //不合格描述
    @Column(name="unqualified_remark")
    private String unqualifiedRemark;
    //附件
    @Column(name="file_url")
    private String fileUrl;

    //收货单号
    @Column(name="delivery_code")
    private String deliveryCode;

    //物料是否合格1-合格，0-不合格
    @Column(name = "unqualified_flag")
    private Integer unqualifiedFlag;

    //列表编号
    @Column(name="incoming_check_number")
    private Integer incomingCheckNumber;

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

    //采购明细id
    @Column(name = "purchase_orders_list_id")
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

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
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

    public String getPurchaseCode() {
        return purchaseCode;
    }

    public void setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
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

    public Integer getPurchaseOrdersId() {
        return purchaseOrdersId;
    }

    public void setPurchaseOrdersId(Integer purchaseOrdersId) {
        this.purchaseOrdersId = purchaseOrdersId;
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

    public String getDeliveryCode() {
        return deliveryCode;
    }

    public void setDeliveryCode(String deliveryCode) {
        this.deliveryCode = deliveryCode;
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

    public Integer getPurchaseOrdersListId() {
        return purchaseOrdersListId;
    }

    public void setPurchaseOrdersListId(Integer purchaseOrdersListId) {
        this.purchaseOrdersListId = purchaseOrdersListId;
    }
}
