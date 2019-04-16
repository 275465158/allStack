package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="material_processing")
public class MaterialProcessingEntity extends BaseCompanyAndStatusEntity {

	@Column(name="material_processing_material_id")
	private Integer materialId;
	
	@Column(name="material_processing_name")
	private String processingName;
	
	@Column(name="material_processing_speciality")
	private String speciality;
	
	@Column(name="material_processing_cost_time")
	private String costTime;
	
	@Column(name="material_processing_special_equipment")
	private String specialEquipment;
	
	@Column(name="material_processing_ordered_by")
	private String orderedBy;
	
	@Column(name="material_processing_comment")
	private String comment;

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public String getProcessingName() {
		return processingName;
	}

	public void setProcessingName(String processingName) {
		this.processingName = processingName;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getCostTime() {
		return costTime;
	}

	public void setCostTime(String costTime) {
		this.costTime = costTime;
	}

	public String getSpecialEquipment() {
		return specialEquipment;
	}

	public void setSpecialEquipment(String specialEquipment) {
		this.specialEquipment = specialEquipment;
	}

	public String getOrderedBy() {
		return orderedBy;
	}

	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
