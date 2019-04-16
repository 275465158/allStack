package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "production_plan")
public class ProductionPlanEntity extends BaseCompanyAndStatusEntity {

    @Column(name="production_number")
    private String productionNumber;

    @Column(name = "product_batch")
    private String productBatch;

    @Column(name = "plan_product_date")
    private String planProductDate;

    @Column(name = "latest_product_date")
    private String latestProductDate;

    @Column(name = "material_num")
    private Float materialNum;

    @Column(name = "original_material")
    private String originalMaterial;

    @Column(name = "material_unit")
    private String materialUnit;

    @Column(name = "production_status")
    private Integer productionStatus;

    @Column(name = "complete_date")
    private String completeDate;

    @Column(name = "production_type")
    private Integer productionType;

    @Column(name = "check_flag")
    private Integer checkFlag;

    @Column(name = "product_type")
    private Integer productType;

    @Column(name = "material_name")
    private String materialName;

    @Column(name = "material_id")
    private Integer materialId;

    @Column(name = "contract_id")
    private String contractId;

    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "prepared_by_id")
    private Integer preparedById;

    @Column(name = "prepared_time")
    private String preparedTime;

    @Column(name = "prepared_by_name")
    private String preparedByName;

    @Column(name = "issue_qty")
    private Float issueQty;

    @Column(name = "parent_production_number")
    private String parentProductionNumber;

    @Column(name = "issue_flag")
    private Integer issueFlag;

    @Column(name="plan_number")
    private Integer planNumber;

    @Column(name = "sequence_number")
    private String sequenceNumber;

    /**工厂物料编码**/
    @Column(name="factory_material_code")
    private String factoryMaterialCode;

    /**物料类型**/
    @Column(name="number")
    private String number;

    /**图号**/
    @Column(name="drawing_code")
    private String drawingCode;

    /**参数**/
    @Column(name="material_bom_param_value_str")
    private String materialBomParamValueStr;

    /**物料来源**/
    @Column(name="source")
    private String source;

    @Column(name="seller_order_product_id")
    private Integer sellerOrderProductId;

    @Column(name="order_need_qty")
    private Float orderNeedQty;

    @Column(name = "material_code")
    private String materialCode;

    public String getProductionNumber() {
        return productionNumber;
    }

    public void setProductionNumber(String productionNumber) {
        this.productionNumber = productionNumber;
    }

    public String getProductBatch() {
        return productBatch;
    }

    public void setProductBatch(String productBatch) {
        this.productBatch = productBatch;
    }

    public String getPlanProductDate() {
        return planProductDate;
    }

    public void setPlanProductDate(String planProductDate) {
        this.planProductDate = planProductDate;
    }

    public String getLatestProductDate() {
        return latestProductDate;
    }

    public void setLatestProductDate(String latestProductDate) {
        this.latestProductDate = latestProductDate;
    }

    public Float getMaterialNum() {
        return materialNum;
    }

    public void setMaterialNum(Float materialNum) {
        this.materialNum = materialNum;
    }

    public String getOriginalMaterial() {
        return originalMaterial;
    }

    public void setOriginalMaterial(String originalMaterial) {
        this.originalMaterial = originalMaterial;
    }

    public String getMaterialUnit() {
        return materialUnit;
    }

    public void setMaterialUnit(String materialUnit) {
        this.materialUnit = materialUnit;
    }

    public Integer getProductionStatus() {
        return productionStatus;
    }

    public void setProductionStatus(Integer productionStatus) {
        this.productionStatus = productionStatus;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }

    public Integer getProductionType() {
        return productionType;
    }

    public void setProductionType(Integer productionType) {
        this.productionType = productionType;
    }

    public Integer getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(Integer checkFlag) {
        this.checkFlag = checkFlag;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getPreparedById() {
        return preparedById;
    }

    public void setPreparedById(Integer preparedById) {
        this.preparedById = preparedById;
    }

    public String getPreparedTime() {
        return preparedTime;
    }

    public void setPreparedTime(String preparedTime) {
        this.preparedTime = preparedTime;
    }

    public String getPreparedByName() {
        return preparedByName;
    }

    public void setPreparedByName(String preparedByName) {
        this.preparedByName = preparedByName;
    }

    public Float getIssueQty() {
        return issueQty;
    }

    public void setIssueQty(Float issueQty) {
        this.issueQty = issueQty;
    }

    public String getParentProductionNumber() {
        return parentProductionNumber;
    }

    public void setParentProductionNumber(String parentProductionNumber) {
        this.parentProductionNumber = parentProductionNumber;
    }

    public Integer getIssueFlag() {
        return issueFlag;
    }

    public void setIssueFlag(Integer issueFlag) {
        this.issueFlag = issueFlag;
    }

    public Integer getPlanNumber() {
        return planNumber;
    }

    public void setPlanNumber(Integer planNumber) {
        this.planNumber = planNumber;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
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

    public Integer getSellerOrderProductId() {
        return sellerOrderProductId;
    }

    public void setSellerOrderProductId(Integer sellerOrderProductId) {
        this.sellerOrderProductId = sellerOrderProductId;
    }

    public Float getOrderNeedQty() {
        return orderNeedQty;
    }

    public void setOrderNeedQty(Float orderNeedQty) {
        this.orderNeedQty = orderNeedQty;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }
}
