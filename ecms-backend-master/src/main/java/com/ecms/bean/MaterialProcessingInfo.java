package com.ecms.bean;

/**
 * 
 * @author alan.wu
 * 2018年8月2日
 * @Description 产品制程实体
 */
public class MaterialProcessingInfo extends BaseCompanyAndStatusInfo {

	//物料id
	private Integer materialId;

	//物料实体
	private MaterialBomInfo materialBom;
	
	//制程名称
	private String processingName;
	
	//特性
	private String speciality;

	//额定工时
	private String costTime;

	//所需设备
	private String specialEquipment;

	//排序
	private String orderedBy;

	//备注
	private String comment;
	
	//页面上使用缓存变量，实现在表格里是否可被编辑
	private Boolean editFlag;
	
	public Boolean getEditFlag() {
		return false;
	}
	
	public void setEditFlag(Boolean editFlag) {
		
	}

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public MaterialBomInfo getMaterialBom() {
		return materialBom;
	}

	public void setMaterialBom(MaterialBomInfo materialBom) {
		this.materialBom = materialBom;
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
