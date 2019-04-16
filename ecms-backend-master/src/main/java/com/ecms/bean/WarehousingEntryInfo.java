package com.ecms.bean;

/**
 * 
 * @author alan.wu
 * 2018年8月2日
 * @Description 入库单实体
 */
public class WarehousingEntryInfo extends BaseCompanyAndStatusInfo {

	//物料id
	private Integer materialId;
	
	//仓库id
	private Integer repertoryId;
	
	//库位
	private String shelfPosition;
	
	//供应商id
	private Integer supplierId;
	
	//数量
	private Float qty;
	
	//批次
	private String materielbatch;
	
	//采购原因
	private String purchaseCause;
	
	//原图材料
	private String originOfMateriel;
	
	//入库时间
	private String stockFrom;
	
	//有效期
	private String expiryDate;
	
	//签收人
	private String consignee;
	
	//预计到货时间
	private String estimatedDate;
	
	//采购订单id
	private Integer purchaseOrderId;

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public Integer getRepertoryId() {
		return repertoryId;
	}

	public void setRepertoryId(Integer repertoryId) {
		this.repertoryId = repertoryId;
	}

	public String getShelfPosition() {
		return shelfPosition;
	}

	public void setShelfPosition(String shelfPosition) {
		this.shelfPosition = shelfPosition;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public Float getQty() {
		return qty;
	}

	public void setQty(Float qty) {
		this.qty = qty;
	}

	public String getMaterielbatch() {
		return materielbatch;
	}

	public void setMaterielbatch(String materielbatch) {
		this.materielbatch = materielbatch;
	}

	public String getPurchaseCause() {
		return purchaseCause;
	}

	public void setPurchaseCause(String purchaseCause) {
		this.purchaseCause = purchaseCause;
	}

	public String getOriginOfMateriel() {
		return originOfMateriel;
	}

	public void setOriginOfMateriel(String originOfMateriel) {
		this.originOfMateriel = originOfMateriel;
	}

	public String getStockFrom() {
		return stockFrom;
	}

	public void setStockFrom(String stockFrom) {
		this.stockFrom = stockFrom;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getEstimatedDate() {
		return estimatedDate;
	}

	public void setEstimatedDate(String estimatedDate) {
		this.estimatedDate = estimatedDate;
	}

	public Integer getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(Integer purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}
	
}
