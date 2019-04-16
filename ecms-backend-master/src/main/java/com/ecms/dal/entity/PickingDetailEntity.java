package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="picking_detail")
public class PickingDetailEntity extends BaseCompanyAndStatusEntity {
	
	@Column(name="picking_detail_picking_id")
	private Integer pickingId;
	
	@Column(name="picking_detail_materiel_id")
	private Integer materielId;
	
	@Column(name="picking_detail_picking_qty")
	private Float qty;
	
	@Column(name="picking_detail_unit")
	private String unit;

	public Integer getPickingId() {
		return pickingId;
	}

	public void setPickingId(Integer pickingId) {
		this.pickingId = pickingId;
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
