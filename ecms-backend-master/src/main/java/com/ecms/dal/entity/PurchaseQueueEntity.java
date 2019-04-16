package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="purchase_queue")
public class PurchaseQueueEntity extends BaseCompanyAndStatusEntity {

	@Column(name="purchase_queue_material_id")
	private Integer materialId;
	
	@Column(name="purchase_queue_qty")
	private Float qty;
	
	@Column(name="purchase_queue_reason")
	private String reason;
	
	@Column(name="purchase_queue_lastest_time")
	private String lastestTime;

	@Column(name="purchase_queue_ordered")
	private Integer ordered;
	
	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public Float getQty() {
		return qty;
	}

	public void setQty(Float qty) {
		this.qty = qty;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getLastestTime() {
		return lastestTime;
	}

	public void setLastestTime(String lastestTime) {
		this.lastestTime = lastestTime;
	}

	public Integer getOrdered() {
		return ordered;
	}

	public void setOrdered(Integer ordered) {
		this.ordered = ordered;
	}
}
