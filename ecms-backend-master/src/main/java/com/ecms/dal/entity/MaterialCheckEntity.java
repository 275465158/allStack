package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="material_check")
public class MaterialCheckEntity extends BaseCompanyAndStatusEntity {

	//物料id
	@Column(name="material_id")
	private Integer materialId;

	//验收id
	@Column(name="check_id")
	private Integer checkId;

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public Integer getCheckId() {
		return checkId;
	}

	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}
}
