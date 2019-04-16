package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="warehousing_entry")
public class WarehousingEntryEntity extends BaseCompanyAndStatusEntity {

	@Column(name="material_id")
	private Integer materialId;
	
	@Column(name="repertory_id")
	private Integer repertoryId;
	
	@Column(name="shelf_position")
	private String shelfPosition;
	
	@Column(name="supplier_id")
	private Integer supplierId;
	
	private Float qty;
	
	@Column(name="materiel_batch")
	private String materielbatch;
	
	@Column(name="purchase_cause")
	private String purchaseCause;
	
	@Column(name="origin_of_materiel")
	private String originOfMateriel;
	
	@Column(name="materiel_stock_from")
	private String stockFrom;
	
	@Column(name="materiel_expiry_date")
	private String expiryDate;
	
	@Column(name="materiel_consignee")
	private String consignee;
	
	@Column(name="estimated_date")
	private String estimatedDate;
	
	@Column(name="purchase_order_id")
	private Integer purchaseOrderId;

	@Column(name="inbound_status")
	private Integer inboundStatus;

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

	public Integer getInboundStatus() {
		return inboundStatus;
	}

	public void setInboundStatus(Integer inboundStatus) {
		this.inboundStatus = inboundStatus;
	}
}
