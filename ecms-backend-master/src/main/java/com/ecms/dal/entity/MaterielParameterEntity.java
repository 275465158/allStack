package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "materielparameter")
public class MaterielParameterEntity extends BaseCompanyAndStatusEntity {

	@Column(name = "materiel_id")
	private Integer materielId;

	@Column(name = "parameter_name")
	private String parameterName;

	@Column(name = "parameter_value")
	private String parameterValue;

	@Column(name = "is_visible")
	private Boolean isVisible;

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

	public Integer getMaterielId() {
		return materielId;
	}

	public void setMaterielId(Integer materielId) {
		this.materielId = materielId;
	}
}
