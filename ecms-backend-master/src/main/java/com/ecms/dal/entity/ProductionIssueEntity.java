package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author Richie
 * @version 2018/9/16 16:54
 */
@Table(name = "produce_issuance")
public class ProductionIssueEntity extends BaseCompanyAndStatusEntity {

    @Column(name="production_number")
    private String productionNumber;

    @Column(name = "material_Id")
    private Integer materialId;

    @Column(name = "issue_qty")
    private Float issueQty;

    @Column(name = "plan_id")
    private Integer planId;

    @Column(name = "issuance_number")
    private String issuanceNumber;

    @Column(name = "used_qty")
    private Float usedQty;

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

    public String getProductionNumber() {
        return productionNumber;
    }

    public void setProductionNumber(String productionNumber) {
        this.productionNumber = productionNumber;
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

    public Float getUsedQty() {
        return usedQty;
    }

    public void setUsedQty(Float usedQty) {
        this.usedQty = usedQty;
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
}
