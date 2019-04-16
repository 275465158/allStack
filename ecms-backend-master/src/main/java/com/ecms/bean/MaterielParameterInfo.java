package com.ecms.bean;

/**
 * 
 * @author alan.wu
 * 2018年8月2日
 * @Description 产品属性实体
 * @deprecated 迁移至物料属性实体
 * @see MaterialParameterInfo
 */
public class MaterielParameterInfo extends BaseCompanyAndStatusInfo {

	//产品id
	private Integer materielId;
	
	//属性名称
	private String parameterName;
	
	//属性值
	private String parameterValue;
	
	//是否列表可见
	private Boolean isVisible;

	public Integer getMaterielId() {
		return materielId;
	}

	public void setMaterielId(Integer materielId) {
		this.materielId = materielId;
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
