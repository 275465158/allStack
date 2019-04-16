package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="material_bom_param_name")
public class MaterialBomParamNameEntity extends BaseCompanyAndStatusEntity {

	//物料id
	@Column(name="material_id")
	private Integer materialId;

	//物料参数名称id
	@Column(name="material_param_name_id")
	private Integer materialParamNameId;

	//物料参数值
	@Column(name="material_param_name_value")
	private String materialParamNameValue;

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public Integer getMaterialParamNameId() {
		return materialParamNameId;
	}

	public void setMaterialParamNameId(Integer materialParamNameId) {
		this.materialParamNameId = materialParamNameId;
	}

	public String getMaterialParamNameValue() {
		return materialParamNameValue;
	}

	public void setMaterialParamNameValue(String materialParamNameValue) {
		this.materialParamNameValue = materialParamNameValue;
	}
}
