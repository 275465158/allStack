package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="material_bom_param_specification")
public class MaterialBomParamSpecificationEntity extends BaseCompanyAndStatusEntity {

	//物料id
	@Column(name="material_id")
	private Integer materialId;

	//物料参数规格名称id
	@Column(name="material_param_specification_id")
	private Integer materialParamSpecificationId;

	//物料参数规格值
	@Column(name="material_param_specification_value")
	private String materialParamSpecificationValue;

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public Integer getMaterialParamSpecificationId() {
		return materialParamSpecificationId;
	}

	public void setMaterialParamSpecificationId(Integer materialParamSpecificationId) {
		this.materialParamSpecificationId = materialParamSpecificationId;
	}

	public String getMaterialParamSpecificationValue() {
		return materialParamSpecificationValue;
	}

	public void setMaterialParamSpecificationValue(String materialParamSpecificationValue) {
		this.materialParamSpecificationValue = materialParamSpecificationValue;
	}
}
