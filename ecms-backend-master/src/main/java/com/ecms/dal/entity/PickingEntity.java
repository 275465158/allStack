package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="picking")
public class PickingEntity extends BaseCompanyAndStatusEntity {

	@Column(name="picking_code")
	private String pickingCode;
	
	@Column(name="picking_production_id")
	private Integer productionId;
	
	@Column(name="picking_useage")
	private String useage;
	
	@Column(name="picking_pick_date")
	private String pickDate;
	
	@Column(name="picking_prepared_by")
	private String preparedBy;
	
	@Column(name="picking_picked_by")
	private String pickedBy;
	
	@Column(name="picking_status")
	private String pickingStatus;

	public String getPickingCode() {
		return pickingCode;
	}

	public void setPickingCode(String pickingCode) {
		this.pickingCode = pickingCode;
	}

	public Integer getProductionId() {
		return productionId;
	}

	public void setProductionId(Integer productionId) {
		this.productionId = productionId;
	}

	public String getUseage() {
		return useage;
	}

	public void setUseage(String useage) {
		this.useage = useage;
	}

	public String getPickDate() {
		return pickDate;
	}

	public void setPickDate(String pickDate) {
		this.pickDate = pickDate;
	}

	public String getPreparedBy() {
		return preparedBy;
	}

	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}

	public String getPickedBy() {
		return pickedBy;
	}

	public void setPickedBy(String pickedBy) {
		this.pickedBy = pickedBy;
	}

	public String getPickingStatus() {
		return pickingStatus;
	}

	public void setPickingStatus(String pickingStatus) {
		this.pickingStatus = pickingStatus;
	}
	
	
	
}
