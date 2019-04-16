package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author Richie
 * @version 2019/1/9 13:46
 */
@Table(name = "produce_material_return_log")
public class ProduceMaterialReturnLogEntity extends BaseCompanyAndStatusEntity {
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

    @Column(name = "return_qty")
    private Float returnQty;

    @Column(name = "issuance_number")
    private String issuanceNumber;

    @Column(name = "operating_personnel_id")
    private Integer operatingPersonnelId;

    @Column(name = "material_batch")
    private String materialBatch;

    @Column(name = "return_number")
    private String returnNumber;

    @Column(name = "material_repertory_detail_id")
    private Integer materialRepertoryDetailId;

    @Column(name = "return_date")
    private String returnDate;

    @Column(name = "plan_id")
    private Integer planId;

    @Column(name = "return_no")
    private Integer returnNo;

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

    public Float getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(Float returnQty) {
        this.returnQty = returnQty;
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

    public String getReturnNumber() {
        return returnNumber;
    }

    public void setReturnNumber(String returnNumber) {
        this.returnNumber = returnNumber;
    }

    public Integer getMaterialRepertoryDetailId() {
        return materialRepertoryDetailId;
    }

    public void setMaterialRepertoryDetailId(Integer materialRepertoryDetailId) {
        this.materialRepertoryDetailId = materialRepertoryDetailId;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getReturnNo() {
        return returnNo;
    }

    public void setReturnNo(Integer returnNo) {
        this.returnNo = returnNo;
    }
}
