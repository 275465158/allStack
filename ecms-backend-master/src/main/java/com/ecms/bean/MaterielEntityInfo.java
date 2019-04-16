package com.ecms.bean;

public class MaterielEntityInfo extends BaseCompanyAndStatusInfo {

	private String materielCode;

	private String materielName;

	private String originalMateriel;

	private String shelfPosition;

	private Double onhand;

	private Double safeStock;

	private Double minPurchaseNum;

	private String purchaseType;

	private String materielUnit;

	private Double requiredByOrders;

	private Integer repertoryId;

	private Integer companyId;

	private Integer materialId;

	private String barCode;

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

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

	public String getOriginalMateriel() {
		return originalMateriel;
	}

	public void setOriginalMateriel(String originalMateriel) {
		this.originalMateriel = originalMateriel;
	}

	public String getShelfPosition() {
		return shelfPosition;
	}

	public void setShelfPosition(String shelfPosition) {
		this.shelfPosition = shelfPosition;
	}

	public Double getOnhand() {
		return onhand;
	}

	public void setOnhand(Double onhand) {
		this.onhand = onhand;
	}

	public Double getSafeStock() {
		return safeStock;
	}

	public void setSafeStock(Double safeStock) {
		this.safeStock = safeStock;
	}

	public Double getMinPurchaseNum() {
		return minPurchaseNum;
	}

	public void setMinPurchaseNum(Double minPurchaseNum) {
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

	public Double getRequiredByOrders() {
		return requiredByOrders;
	}

	public void setRequiredByOrders(Double requiredByOrders) {
		this.requiredByOrders = requiredByOrders;
	}

	public Integer getRepertoryId() {
		return repertoryId;
	}

	public void setRepertoryId(Integer repertoryId) {
		this.repertoryId = repertoryId;
	}

	@Override
	public Integer getCompanyId() {
		return companyId;
	}

	@Override
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}
}
