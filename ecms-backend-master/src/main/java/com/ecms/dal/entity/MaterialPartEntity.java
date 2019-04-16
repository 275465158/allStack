package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="material_part")
public class MaterialPartEntity extends AbstractEntity {
	
	@Column(name="material_parent_id")
	private Integer parentId;
	
	@Column(name="material_id")
	private Integer materialBomId;
	
	@Column(name="material_part_original_code")
	private String originalCode;
	
	@Column(name="material_part_original_name")
	private String originalName;
	
	@Column(name="material_part_consume_qty")
	private Float consumeQty;
	
	@Column(name="material_part_original_unit")
	private String originalUnit;
	
	@Column(name="material_part_purchase_type")
	private Integer purchaseType;
	
	@Column(name="material_part_reusable")
	private String reusable;

	@Column(name="status")
	private Integer status;

	@Column(name="company_id")
	private Integer companyId;

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getMaterialBomId() {
		return materialBomId;
	}

	public void setMaterialBomId(Integer materialBomId) {
		this.materialBomId = materialBomId;
	}

	public String getOriginalCode() {
		return originalCode;
	}

	public void setOriginalCode(String originalCode) {
		this.originalCode = originalCode;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public Float getConsumeQty() {
		return consumeQty;
	}

	public void setConsumeQty(Float consumeQty) {
		this.consumeQty = consumeQty;
	}

	public String getOriginalUnit() {
		return originalUnit;
	}

	public void setOriginalUnit(String originalUnit) {
		this.originalUnit = originalUnit;
	}

	public Integer getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(Integer purchaseType) {
		this.purchaseType = purchaseType;
	}

	public String getReusable() {
		return reusable;
	}

	public void setReusable(String reusable) {
		this.reusable = reusable;
	}
	
	
}
