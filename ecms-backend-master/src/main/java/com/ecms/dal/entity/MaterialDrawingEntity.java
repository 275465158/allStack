package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="material_drawing")
public class MaterialDrawingEntity extends BaseCompanyAndStatusEntity {

	//物料id
	@Column(name="material_id")
	private Integer materialId;

	//图纸id
	@Column(name="drawing_id")
	private Integer drawingId;

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public Integer getDrawingId() {
		return drawingId;
	}

	public void setDrawingId(Integer drawingId) {
		this.drawingId = drawingId;
	}
}
