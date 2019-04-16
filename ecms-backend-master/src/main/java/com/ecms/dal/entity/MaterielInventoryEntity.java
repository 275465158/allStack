package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="materiel_inventory_detail")
public class MaterielInventoryEntity extends BaseCompanyAndStatusEntity {
	
	@Column(name="materiel_id")
	private Integer materielId;
	
	@Column(name="shelf_position")
	private String shelfPosition;
	
	@Column(name="supplier_id")
	private Integer supplierId;
	
	@Column(name="materiel_batch")
	private String materielBatch;
	
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
	
	@Column(name="entry_id")
	private Integer entryId;
	
	@Column(name="qty")
	private Float qty;

	public Integer getMaterielId() {
		return materielId;
	}

	public void setMaterielId(Integer materielId) {
		this.materielId = materielId;
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

	public String getMaterielBatch() {
		return materielBatch;
	}

	public void setMaterielBatch(String materielBatch) {
		this.materielBatch = materielBatch;
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

	public Integer getEntryId() {
		return entryId;
	}

	public void setEntryId(Integer entryId) {
		this.entryId = entryId;
	}

	public Float getQty() {
		return qty;
	}

	public void setQty(Float qty) {
		this.qty = qty;
	}

}
