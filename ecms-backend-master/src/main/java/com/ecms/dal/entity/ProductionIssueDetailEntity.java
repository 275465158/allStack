package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author Richie
 * @version 2018/9/16 16:54
 */
@Table(name = "produce_issuance_detail")
public class ProductionIssueDetailEntity extends BaseCompanyAndStatusEntity {
    @Column(name = "repertory_id")
    private Integer repertoryId;

    @Column(name="production_number")
    private String productionNumber;

    @Column(name="material_name")
    private String materialName;

    @Column(name="contract_id")
    private String contractId;

    @Column(name = "material_Id")
    private Integer materialId;

    @Column(name = "issue_qty")
    private Float issueQty;

    @Column(name = "plan_id")
    private Integer planId;

    @Column(name = "issuance_number")
    private String issuanceNumber;

    @Column(name = "operating_personnel_id")
    private Integer operatingPersonnelId;

    @Column(name = "material_batch")
    private String materialBatch;

    @Column(name = "material_repertory_detail_id")
    private Integer materialRepertoryDetailId;

    @Column(name = "issuance_id")
    private Integer issuanceId;
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

    @Column(name = "issuance_no")
    private Integer issuanceNo;

    @Column(name = "used_qty")
    private Float usedQty;

    public Integer getRepertoryId() {
        return repertoryId;
    }

    public void setRepertoryId(Integer repertoryId) {
        this.repertoryId = repertoryId;
    }

    public String getProductionNumber() {
        return productionNumber;
    }

    public void setProductionNumber(String productionNumber) {
        this.productionNumber = productionNumber;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public Float getIssueQty() {
        return issueQty;
    }

    public void setIssueQty(Float issueQty) {
        this.issueQty = issueQty;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getIssuanceNumber() {
        return issuanceNumber;
    }

    public void setIssuanceNumber(String issuanceNumber) {
        this.issuanceNumber = issuanceNumber;
    }

    public Integer getOperatingPersonnelId() {
        return operatingPersonnelId;
    }

    public void setOperatingPersonnelId(Integer operatingPersonnelId) {
        this.operatingPersonnelId = operatingPersonnelId;
    }

    public String getMaterialBatch() {
        return materialBatch;
    }

    public void setMaterialBatch(String materialBatch) {
        this.materialBatch = materialBatch;
    }

    public Integer getMaterialRepertoryDetailId() {
        return materialRepertoryDetailId;
    }

    public void setMaterialRepertoryDetailId(Integer materialRepertoryDetailId) {
        this.materialRepertoryDetailId = materialRepertoryDetailId;
    }

    public Integer getIssuanceId() {
        return issuanceId;
    }

    public void setIssuanceId(Integer issuanceId) {
        this.issuanceId = issuanceId;
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

    public Integer getIssuanceNo() {
        return issuanceNo;
    }

    public void setIssuanceNo(Integer issuanceNo) {
        this.issuanceNo = issuanceNo;
    }

    public Float getUsedQty() {
        return usedQty;
    }

    public void setUsedQty(Float usedQty) {
        this.usedQty = usedQty;
    }
}
