package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="delivery_detail")
public class DeliveryDetailEntity extends BaseCompanyAndStatusEntity {

	@Column(name="delivery_detail_delivery_id")
	private Integer deliveryId;
	
	@Column(name="delivery_detail_materiel_id")
	private Integer materielId;
	
	@Column(name="delivery_detail_delivery_qty")
	private Float qty;
	
	@Column(name="delivery_detail_unit")
	private String unit;

	public Integer getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(Integer deliveryId) {
		this.deliveryId = deliveryId;
	}

	public Integer getMaterielId() {
		return materielId;
	}

	public void setMaterielId(Integer materielId) {
		this.materielId = materielId;
	}

	public Float getQty() {
		return qty;
	}

	public void setQty(Float qty) {
		this.qty = qty;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
}
