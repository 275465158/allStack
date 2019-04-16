package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "supplier_material")
public class SupplierMaterialEntity extends BaseCompanyAndStatusEntity {

	@Column(name = "supplier_id")
	private Integer supplierId;
	
	@Column(name = "material_id")
	private Integer materialId;

	@Column(name = "qualified_percent")
	private String qualifiedPercent;

	@Column(name = "price")
	private Float price;

	@Column(name = "min_qty")
	private Float minQty;

	@Column(name = "purchase_method")
	private String purchaseMethod;

	@Column(name = "purchase_days")
	private String purchaseDays;

	@Column(name = "supplier_material_repertory_id")
	private Integer repertoryId;

	@Column(name = "weight")
	private Double weight ;

	@Column(name = "material_name")
	private String materialName;

	@Column(name = "original_material")
	private String originalMaterial;

	@Column(name = "material_bom_param_value_str")
	private String materialBomParamValueStr;

	@Column(name = "material_code")
	private String materialCode;

	@Column(name = "type")
	private String type;

	@Column(name = "factory_material_code")
	private String factoryMaterialCode;

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Integer getMaterialId() {
		return materialId;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getMinQty() {
		return minQty;
	}

	public void setMinQty(Float minQty) {
		this.minQty = minQty;
	}

	public String getPurchaseMethod() {
		return purchaseMethod;
	}

	public void setPurchaseMethod(String purchaseMethod) {
		this.purchaseMethod = purchaseMethod;
	}

	public String getPurchaseDays() {
		return purchaseDays;
	}

	public void setPurchaseDays(String purchaseDays) {
		this.purchaseDays = purchaseDays;
	}

	public Integer getRepertoryId() {
		return repertoryId;
	}

	public void setRepertoryId(Integer repertoryId) {
		this.repertoryId = repertoryId;
	}

	public String getQualifiedPercent() {
		return qualifiedPercent;
	}

	public void setQualifiedPercent(String qualifiedPercent) {
		this.qualifiedPercent = qualifiedPercent;
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

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFactoryMaterialCode() {
		return factoryMaterialCode;
	}

	public void setFactoryMaterialCode(String factoryMaterialCode) {
		this.factoryMaterialCode = factoryMaterialCode;
	}
}
