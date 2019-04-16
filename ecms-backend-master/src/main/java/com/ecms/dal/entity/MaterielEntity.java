package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "materiel")
public class MaterielEntity extends BaseCompanyAndStatusEntity {

	@Column(name = "material_id")
	private Integer materialId;

	@Column(name = "onhand")
	private Float onhand;

	@Column(name = "safe_stock")
	private Float safeStock;

	@Column(name = "min_purchase_num")
	private Float minPurchaseNum;

	@Column(name = "purchase_type")
	private String purchaseType;

	@Column(name = "materiel_unit")
	private String materielUnit;

	@Column(name = "required_by_orders")
	private Float requiredByOrders;

	@Column(name="repertory_id")
	private Integer repertoryId;

	@Column(name = "materiel_code")
	private String materielCode;

	@Column(name = "materiel_name")
	private String materielName;

	@Column(name = "shelf_position")
	private String shelfPosition;

	public String getMaterielCode() {
		return materielCode;
	}

	public void setMaterielCode(String materielCode) {
		this.materielCode = materielCode;
	}

	public String getMaterielName() {
		return materielName;
	}

	public void setMaterielName(String materielName) {
		this.materielName = materielName;
	}

	public String getShelfPosition() {
		return shelfPosition;
	}

	public void setShelfPosition(String shelfPosition) {
		this.shelfPosition = shelfPosition;
	}

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}


	public Float getOnhand() {
		return onhand;
	}

	public void setOnhand(Float onhand) {
		this.onhand = onhand;
	}

	public Float getSafeStock() {
		return safeStock;
	}

	public void setSafeStock(Float safeStock) {
		this.safeStock = safeStock;
	}

	public Float getMinPurchaseNum() {
		return minPurchaseNum;
	}

	public void setMinPurchaseNum(Float minPurchaseNum) {
		this.minPurchaseNum = minPurchaseNum;
	}

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public String getMaterielUnit() {
		return materielUnit;
	}

	public void setMaterielUnit(String materielUnit) {
		this.materielUnit = materielUnit;
	}

	public Float getRequiredByOrders() {
		return requiredByOrders;
	}

	public void setRequiredByOrders(Float requiredByOrders) {
		this.requiredByOrders = requiredByOrders;
	}

	public Integer getRepertoryId() {
		return repertoryId;
	}

	public void setRepertoryId(Integer repertoryId) {
		this.repertoryId = repertoryId;
	}	
}
