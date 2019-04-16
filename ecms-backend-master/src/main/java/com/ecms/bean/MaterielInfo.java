package com.ecms.bean;

import java.util.List;

public class MaterielInfo extends BaseCompanyAndStatusInfo {
	
	//物料id
	private Integer materialId;
	
	//物料实体
	private MaterialBomInfo materialBom;
	
	//库存数
	private Float onhand;
	
	//最小采购量
	private Float minPurchaseNum;
	
	//安全库存
	private Float safeStock;
	
	//采购类型
	private String purchaseType;
	
	//计量单位
	private String materielUnit;
	
	//订单需求数
	private Float RequiredByOrders;
	
	//仓库id
	private Integer repertoryId;
	
	//库存明细
	private List<MaterielInventoryInfo> inventoryList;

	//物料编号
	private String materielCode;

	//物料名称
	private String materielName;

	//物料位置
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

	public void setMaterialId(Integer integer) {
		this.materialId = integer;
	}

	public MaterialBomInfo getMaterialBom() {
		return materialBom;
	}

	public void setMaterialBom(MaterialBomInfo materialBom) {
		this.materialBom = materialBom;
	}

	public Float getOnhand() {
		return onhand;
	}

	public void setOnhand(Float onhand) {
		this.onhand = onhand;
	}

	public Float getMinPurchaseNum() {
		return minPurchaseNum;
	}

	public void setMinPurchaseNum(Float minPurchaseNum) {
		this.minPurchaseNum = minPurchaseNum;
	}

	public Float getSafeStock() {
		return safeStock;
	}

	public void setSafeStock(Float safeStock) {
		this.safeStock = safeStock;
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
		return RequiredByOrders;
	}

	public void setRequiredByOrders(Float requiredByOrders) {
		RequiredByOrders = requiredByOrders;
	}

	public Integer getRepertoryId() {
		return repertoryId;
	}

	public void setRepertoryId(Integer repertoryId) {
		this.repertoryId = repertoryId;
	}

	public List<MaterielInventoryInfo> getInventoryList() {
		return inventoryList;
	}

	public void setInventoryList(List<MaterielInventoryInfo> inventoryList) {
		this.inventoryList = inventoryList;
	}

}
