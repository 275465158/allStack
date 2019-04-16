package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="material_parameter")
public class MaterialParameterEntity extends BaseCompanyAndStatusEntity {

	@Column(name="material_id")
	private Integer materialId;
	@Column(name="parameter_name")
	private String parameterName;
	@Column(name="parameter_value")
	private String parameterValue;
	@Column(name="is_visible")
	private Boolean isVisible;
	public Integer getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public String getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	public Boolean getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	
}
